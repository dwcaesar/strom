package de.wohlers.strom.Views;

import de.wohlers.strom.DAO.MemberDAO;
import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Models.Member;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Members implements Initializable {

    public Button                      editButton;
    public Button                      deleteButton;
    public TableView<Member>           memberTable;
    public TableColumn<Member, String> nameColumn;

    private final MemberDAO memberDAO = MemberDAO.getInstance();

    public void showNewMemberDialog() {
        Member member = new Member();
        EditMemberDialog.open(member, this::addMember);
    }

    public void showEditMemberDialog() {
        Member member = memberTable.getSelectionModel().getSelectedItem();
        EditMemberDialog.open(member, this::updateMember);
    }

    public void showDeleteMemberDialog() {
        ButtonType buttonDelete = new ButtonType(Lang.get("Generic.Button.Delete"), ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType(Lang.get("Generic.Button.Cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Lang.get("Dialog.Content.Member.Delete"), buttonCancel, buttonDelete);
        alert.setTitle(Lang.get("Dialog.Title.Delete"));
        alert.setHeaderText(Lang.get("Dialog.Header.Member.Delete"));
        alert.setResizable(true);
        alert.setResultConverter(this::onDeleteSelected);
        alert.show();
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
        service.start();
    }

    private void onSelectionChange(ListChangeListener.Change<? extends Member> change) {
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
        // TODO - Muss ich ein Event feuern, damit die Tabelle aktualisiert wird?
    }

    private ButtonType onDeleteSelected(ButtonType b) {
        if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            memberTable.getItems().removeAll(memberTable.getSelectionModel().getSelectedItems());
        }
        return b;
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
