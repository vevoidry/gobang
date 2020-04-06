package com.homework;

import com.homework.data.MetaData;
import com.homework.panel.ButtonHBox;
import com.homework.panel.GobangBoardPanel;
import com.homework.panel.pve.PveGobangBoardPanel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApplication extends Application {
	// ��������
	public static void main(String[] args) {
		launch(args);
	}

	// ����壬���������������
	public static BorderPane root = new BorderPane();
	// �˵���
	public static MenuBar menuBar = new MenuBar();
	// ��ʼ�˵�
	public static Menu startMenu = new Menu("��ʼ");
	// �Ӳ˵�
	public static MenuItem pvePlayerBlackMenuItem = new MenuItem("����ģʽ�����ִ������");
	public static MenuItem pveMachineBlackMenuItem = new MenuItem("����ģʽ������ִ������");
	public static MenuItem pvpMenuItem = new MenuItem("˫��ģʽ");

	// �������������ô��ڵĿ�ߵ�
	public static Scene scene = new Scene(root, (MetaData.SIDE_NUMBER-1 ) * MetaData.SIDE_WIDTH_UNIT,
			(MetaData.SIDE_NUMBER - 1) * MetaData.SIDE_WIDTH_UNIT +50, Color.WHITE);

	// ��̬�����
	static {
		// Ϊ�Ӳ˵����¼�
		pvePlayerBlackMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// �½����̶��󣬴���false���������ִ������
				PveGobangBoardPanel gobangBoardPanel = new PveGobangBoardPanel(false);
				// ʹ���̺Ͱ�ť��ʾ
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		pveMachineBlackMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// �½����̶��󣬴���true�����ǵ���ִ������
				PveGobangBoardPanel gobangBoardPanel = new PveGobangBoardPanel(true);
				// ʹ���̺Ͱ�ť��ʾ
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		pvpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// �½����̶���
				GobangBoardPanel gobangBoardPanel = new GobangBoardPanel();
				// ʹ���̺Ͱ�ť��ʾ
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		// ���Ӳ˵����õ���ʼ�˵��£���ͨ���ָ��߷�Ϊ��������
		startMenu.getItems().addAll(pvePlayerBlackMenuItem, pveMachineBlackMenuItem, new SeparatorMenuItem(),
				pvpMenuItem);
		// ����ʼ�˵���ӵ��˵�����
		menuBar.getMenus().addAll(startMenu);
		// ���˵����ŵ��������
		root.setTop(menuBar);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// ���û���������ʹ��ɼ�
		primaryStage.setTitle("����JavaFX�ļ���������");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
