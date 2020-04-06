package com.homework.panel;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

//面板，用于存储按钮
public class ButtonHBox extends HBox {

	// 需要传入一个棋盘对象，这些按钮就用于管理该棋盘对象
	public ButtonHBox(GobangBoardPanel gobangBoardPanel) {
		// 新建三个按钮对象
		Button peaceButton = new Button("求和");
		Button defeatButton = new Button("认输");
		Button redoButton = new Button("悔棋");
		// 将按钮对象放入面板中
		this.getChildren().addAll(peaceButton, defeatButton, redoButton);
		// 为按钮绑定事件
		peaceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 弹出选择框
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("信息提示");
				alert.setHeaderText("求和");
				if (gobangBoardPanel.is_black) {
					alert.setContentText("黑方请求和棋");
				} else {
					alert.setContentText("白方请求和棋");
				}
				// 获取点击结果并判断，然后弹出提示信息
				Optional result = alert.showAndWait();
				// 弹出提示框
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("回应");
				if (result.get() == ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("白方同意和棋");
					// 保存棋谱
					gobangBoardPanel.history.add("黑方请求和棋，白方同意和棋");
					gobangBoardPanel.saveHistory();
				} else if (result.get() == ButtonType.OK && !gobangBoardPanel.is_black) {
					x.headerTextProperty().set("黑方同意和棋");
					gobangBoardPanel.history.add("白方请求和棋，黑方同意和棋");
					gobangBoardPanel.saveHistory();
				} else if (result.get() != ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("白方拒绝和棋");
				} else {
					x.headerTextProperty().set("黑方拒绝和棋");
				}
				x.showAndWait();
			}
		});
		defeatButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("认输");
				// 判断目前该由哪方落子以便知道是哪方认输
				if (gobangBoardPanel.is_black) {
					x.headerTextProperty().set("黑方认输");
					gobangBoardPanel.history.add("黑方认输，白方获胜");
				} else {
					x.headerTextProperty().set("白方认输");
					gobangBoardPanel.history.add("白方认输，黑方获胜");
				}
				// 保存棋谱
				gobangBoardPanel.saveHistory();
				x.showAndWait();
			}
		});
		redoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//				System.out.println("悔棋");
				// 如果数组中的数据太小，说明没有可以退回的棋步，什么都不做直接返回
				if (gobangBoardPanel.history.size() < 2) {
					return;
				}
				// 弹出选择框
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("信息提示");
				alert.setHeaderText("悔棋");
				if (gobangBoardPanel.is_black) {
					alert.setContentText("黑方请求悔棋");
				} else {
					alert.setContentText("白方请求悔棋");
				}
				// 获取点击结果并判断，然后弹出提示信息
				Optional result = alert.showAndWait();
				// 弹出提示框
				Alert x = new Alert(AlertType.INFORMATION);
				x.titleProperty().set("回应");
				if (result.get() == ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("白方同意悔棋");
					gobangBoardPanel.regret();
				} else if (result.get() == ButtonType.OK && !gobangBoardPanel.is_black) {
					x.headerTextProperty().set("黑方同意悔棋");
					gobangBoardPanel.regret();
				} else if (result.get() != ButtonType.OK && gobangBoardPanel.is_black) {
					x.headerTextProperty().set("白方拒绝悔棋");
				} else {
					x.headerTextProperty().set("黑方拒绝悔棋");
				}
				x.showAndWait();
			}
		});

	}
}
