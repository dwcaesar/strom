package de.wohlers.strom.Views;

import de.wohlers.strom.Models.Member;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public class EditMemberDialog {
    private Member member;
    private Consumer<Member> consumer;

    public static void open(Member member, Consumer<Member> consumer) {
        try {
            FXMLLoader loader = new FXMLLoader(EditMemberDialog.class.getResource("EditMemberDialog.fxml"));
            Parent     parent = loader.load();
            EditMemberDialog controller = loader.getController();
            controller.setMember(member);
            controller.setConsumer(consumer);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LoggerFactory.getLogger(EditMemberDialog.class).error("Konnte Dialog nicht öffnen", e);
        }
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setConsumer(Consumer<Member> consumer) {
        this.consumer = consumer;
    }

    public Consumer<Member> getConsumer() {
        return consumer;
    }
}
