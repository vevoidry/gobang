package com.homework.panel;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

//��壬���ڴ洢��ť
public class ButtonHBox extends HBox {

	// ��Ҫ����һ�����̶�����Щ��ť�����ڹ�������̶���
	public ButtonHBox(GobangBoardPanel gobangBoardPanel) {
		// �½�������ť����
		Button peaceButton = new Button("���");
		Button defeatButton = new Button("����");
		Button redoButton = new Button("����");
		// ����ť������������
		this.getChildren().addAll(peaceButton, defeatButton, redoButton);
		// Ϊ��ť���¼�
		peaceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// ����ѡ���
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("��Ϣ��ʾ");
				alert.setHeaderText("���");
				if (gobangBoardPanel.is_black) {
					alert.setContentText("�ڷ��������");
				} else {
					alert.setContentText("�׷��������");
				}
				// ��ȡ���������жϣ�Ȼ�󵯳���ʾ��Ϣ
				Optional result = alert.showAndWait();
				// ������ʾ��
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("��Ӧ");
				if (result.get() == ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�׷�ͬ�����");
					// ��������
					gobangBoardPanel.history.add("�ڷ�������壬�׷�ͬ�����");
					gobangBoardPanel.saveHistory();
				} else if (result.get() == ButtonType.OK && !gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�ڷ�ͬ�����");
					gobangBoardPanel.history.add("�׷�������壬�ڷ�ͬ�����");
					gobangBoardPanel.saveHistory();
				} else if (result.get() != ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�׷��ܾ�����");
				} else {
					x.headerTextProperty().set("�ڷ��ܾ�����");
				}
				x.showAndWait();
			}
		});
		defeatButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("����");
				// �ж�Ŀǰ�����ķ������Ա�֪�����ķ�����
				if (gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�ڷ�����");
					gobangBoardPanel.history.add("�ڷ����䣬�׷���ʤ");
				} else {
					x.headerTextProperty().set("�׷�����");
					gobangBoardPanel.history.add("�׷����䣬�ڷ���ʤ");
				}
				// ��������
				gobangBoardPanel.saveHistory();
				x.showAndWait();
			}
		});
		redoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//				System.out.println("����");
				// ��������е�����̫С��˵��û�п����˻ص��岽��ʲô������ֱ�ӷ���
				if (gobangBoardPanel.history.size() < 2) {
					return;
				}
				// ����ѡ���
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("��Ϣ��ʾ");
				alert.setHeaderText("����");
				if (gobangBoardPanel.is_black) {
					alert.setContentText("�ڷ��������");
				} else {
					alert.setContentText("�׷��������");
				}
				// ��ȡ���������жϣ�Ȼ�󵯳���ʾ��Ϣ
				Optional result = alert.showAndWait();
				// ������ʾ��
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("��Ӧ");
				if (result.get() == ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�׷�ͬ�����");
					gobangBoardPanel.regret();
				} else if (result.get() == ButtonType.OK && !gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�ڷ�ͬ�����");
					gobangBoardPanel.regret();
				} else if (result.get() != ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("�׷��ܾ�����");
				} else {
					x.headerTextProperty().set("�ڷ��ܾ�����");
				}
				x.showAndWait();
			}
		});

	}
}
