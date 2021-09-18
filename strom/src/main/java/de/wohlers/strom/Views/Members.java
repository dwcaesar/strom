package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Models.Member;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Members implements Initializable {

    private final Logger logger = Logger.getLogger(Members.class.getName());

    @FXML
    private Button                      editButton;
    @FXML
    private Button                      deleteButton;
    @FXML
    private TableView<Member>           memberTable;
    @FXML
    private TableColumn<Member, String> nameColumn;

    private final MemberDAO memberDAO = MemberDAO.getInstance();

    public void showNewMemberDialog() {
        Member member = new Member();
        EditMemberDialog.open(member, this::addMember, this::deleteMember);
    }

    public void showEditMemberDialog() {
        Member member = memberTable.getSelectionModel().getSelectedItem();
        EditMemberDialog.open(member, this::updateMember, this::deleteMember);
    }

    public void showDeleteMemberDialog() {
        DeleteDialog.open(memberTable.getSelectionModel().getSelectedItem(), this::deleteMember, Lang.get("Dialog.Member.Delete"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        memberTable.getSelectionModel().getSelectedItems().addListener(this::onSelectionChange);
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        loadDataAsync();
    }

    private void loadDataAsync() {
        final Service<List<Member>> service = new Service<>() {
            @Override
            protected Task<List<Member>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Member> call() {
                        return memberDAO.findAll();
                    }
                };
            }
        };
        service.setOnSucceeded(e -> {
            memberTable.getItems().setAll(service.getValue());
            memberTable.getItems().addListener(this::persistChange);
        });
        service.setOnFailed(e -> logger.severe("load data has failed " + service.getException().toString()));
        service.start();
    }

    private void onSelectionChange(ListChangeListener.Change<? extends Member> c) {
        if (memberTable.getSelectionModel().isEmpty()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    private void addMember(Member member) {
        memberTable.getItems().add(member);
    }

    private void updateMember(Member member) {
        memberDAO.merge(member);
    }

    private void deleteMember(Member member) {
        memberTable.getItems().remove(member);
    }

    private void persistChange(ListChangeListener.Change<? extends Member> m) {
        while (m.next()) {
            if (m.wasAdded()) {
                m.getAddedSubList().forEach(memberDAO::persist);
            }
            if (m.wasRemoved()) {
                m.getRemoved().forEach(memberDAO::remove);
            }
        }
    }
}
