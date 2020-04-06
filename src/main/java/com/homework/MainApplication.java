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
	// 启动方法
	public static void main(String[] args) {
		launch(args);
	}

	// 根面板，用于容纳其他组件
	public static BorderPane root = new BorderPane();
	// 菜单栏
	public static MenuBar menuBar = new MenuBar();
	// 开始菜单
	public static Menu startMenu = new Menu("开始");
	// 子菜单
	public static MenuItem pvePlayerBlackMenuItem = new MenuItem("单机模式：玩家执黑先行");
	public static MenuItem pveMachineBlackMenuItem = new MenuItem("单机模式：电脑执黑先行");
	public static MenuItem pvpMenuItem = new MenuItem("双人模式");

	// 基础环境，设置窗口的宽高等
	public static Scene scene = new Scene(root, (MetaData.SIDE_NUMBER-1 ) * MetaData.SIDE_WIDTH_UNIT,
			(MetaData.SIDE_NUMBER - 1) * MetaData.SIDE_WIDTH_UNIT +50, Color.WHITE);

	// 静态代码块
	static {
		// 为子菜单绑定事件
		pvePlayerBlackMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 新建棋盘对象，传入false代表是玩家执黑先行
				PveGobangBoardPanel gobangBoardPanel = new PveGobangBoardPanel(false);
				// 使棋盘和按钮显示
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		pveMachineBlackMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 新建棋盘对象，传入true代表是电脑执黑先行
				PveGobangBoardPanel gobangBoardPanel = new PveGobangBoardPanel(true);
				// 使棋盘和按钮显示
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		pvpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 新建棋盘对象
				GobangBoardPanel gobangBoardPanel = new GobangBoardPanel();
				// 使棋盘和按钮显示
				root.setCenter(null);
				root.setCenter(gobangBoardPanel);
				root.setBottom(null);
				root.setBottom(new ButtonHBox(gobangBoardPanel));
			}
		});
		// 将子菜单放置到开始菜单下，并通过分割线分为两个部分
		startMenu.getItems().addAll(pvePlayerBlackMenuItem, pveMachineBlackMenuItem, new SeparatorMenuItem(),
				pvpMenuItem);
		// 将开始菜单添加到菜单栏中
		menuBar.getMenus().addAll(startMenu);
		// 将菜单栏放到根面板中
		root.setTop(menuBar);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 配置基本环境并使其可见
		primaryStage.setTitle("基于JavaFX的简易五子棋");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
