package de.wohlers.strom.Views;

import de.wohlers.strom.Lang.Lang;
import de.wohlers.strom.Models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Members implements Initializable {

    public Button                      editButton;
    public Button                      deleteButton;
    public TableView<Member>           memberTable;
    public TableColumn<Member, String> nameColumn;

    public void showNewMemberDialog() {
        // TODO - EditMemberDialog anzeigen, mit einem leeren Mitglied
        ObservableList<Member> items = FXCollections.observableArrayList(new Member("Test"));
        memberTable.setItems(items);
    }

    public void showEditMemberDialog() {
        // TODO - EditMemberDialog anzeigen, mit Ausgewählten Mitglied
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

    private ButtonType onDeleteSelected(ButtonType b) {
        if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            // TODO - Löschung auch ausführen, vielleicht als Observer auf memberTable.getItems()
            memberTable.getItems().removeAll(memberTable.getSelectionModel().getSelectedItems());
        }
        return b;
    }
}
