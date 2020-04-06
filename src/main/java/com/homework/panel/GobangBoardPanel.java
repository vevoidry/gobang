package com.homework.panel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.homework.data.MetaData;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class GobangBoardPanel extends BorderPane {
	// 垂直面板，用来存储纵向上的线条，用于画棋盘，传入参数为间距，1估计是线条本身所占的宽度，所以间距需要减去该宽度
	public VBox vBox = new VBox(MetaData.SIDE_WIDTH_UNIT - 1);
	// 水平面板，用来存储横向上的线条，用于画棋盘，传入参数为间距
	public HBox hBox = new HBox(MetaData.SIDE_WIDTH_UNIT - 1);
	// 是否轮到黑方落子，true为轮到黑方落子，false为轮到白方落子，执黑先行，因此初始值为true
	public boolean is_black = true;
	// 棋盘每个位置的落子情况
	public Integer[][] array = new Integer[MetaData.SIDE_NUMBER][MetaData.SIDE_NUMBER];
	// 落子的历史记录
	public ArrayList<String> history = new ArrayList<String>();
	// 存储所有棋子对象
	public ArrayList<Ellipse> list = new ArrayList<Ellipse>();

	public GobangBoardPanel() {
		// 画棋盘
		drawGobangBoard();
		// 初始化棋盘数组
		initGobangArray();
		// 监听点击事件
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// 计算出距离鼠标点击处最近的棋盘落子点所对应的数组索引
				int x = (int) Math.round(event.getX() / MetaData.SIDE_WIDTH_UNIT);
				int y = (int) Math.round(event.getY() / MetaData.SIDE_WIDTH_UNIT);
				// 在该落子点绘制棋子
				draw(x, y);
			}
		});
	}

	// 绘制棋盘
	public void drawGobangBoard() {
		// 设置棋盘背景色
		this.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
		// 画横线
		for (int i = 0; i < MetaData.SIDE_NUMBER; i++) {
			Line line = new Line();
			int startX = 0;
			int startY = i * MetaData.SIDE_WIDTH_UNIT;
			int endX = (MetaData.SIDE_NUMBER - 1) * MetaData.SIDE_WIDTH_UNIT;
			int endY = i * MetaData.SIDE_WIDTH_UNIT;
			line.setStartX(startX);
			line.setStartY(startY);
			line.setEndX(endX);
			line.setEndY(endY);
			vBox.getChildren().add(line);
		}
		// 画纵线
		for (int i = 0; i < MetaData.SIDE_NUMBER; i++) {
			Line line = new Line();
			int startX = i * MetaData.SIDE_WIDTH_UNIT;
			int startY = 0;
			int endX = i * MetaData.SIDE_WIDTH_UNIT;
			int endY = (MetaData.SIDE_NUMBER - 1) * MetaData.SIDE_WIDTH_UNIT;
			line.setStartX(startX);
			line.setStartY(startY);
			line.setEndX(endX);
			line.setEndY(endY);
			hBox.getChildren().add(line);
		}
		// 将横纵线添加到面板中，形成棋盘
		this.getChildren().add(vBox);
		this.getChildren().add(hBox);
	}

	// 将存储棋子的数组全部初始化为空子
	public void initGobangArray() {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = MetaData.STATUS_BLANK;
			}
		}
	}

	// 落子，传入相应数组上的索引，并绘制棋子
	public void draw(int x, int y) {
		// 判断所点击的位置是否不存在棋子
		if (array[x][y] != MetaData.STATUS_BLANK) {
			// 如果存在棋子，则什么都不做
			return;
		} else {
			// 判断是由黑方落子还是白方落子
			// 将棋子在棋盘上画出后在修改棋子数组上的相应数据
			Ellipse ellipse = new Ellipse();
			ellipse.setCenterX(x * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setCenterY(y * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setRadiusX(MetaData.RADIUS);
			ellipse.setRadiusY(MetaData.RADIUS);
			if (is_black) {
				ellipse.setFill(Color.BLACK);
				array[x][y] = MetaData.STATUS_BLACK;
				// 将数据保存到棋谱中
				history.add("黑子(" + x + "," + y + ");");
			} else {
				ellipse.setFill(Color.WHITE);
				array[x][y] = MetaData.STATUS_WHITE;
				history.add("白子(" + x + "," + y + ");");
			}
			// 在棋盘上添加棋子，使棋子可见
			this.getChildren().add(ellipse);
			// 将棋子放入棋子列表，用于悔棋
			list.add(ellipse);
			// 转换落子方
			is_black = !is_black;
			// 判断此时是否连成五子，若连成五子则提示游戏结束并保存棋谱
			if (isEnd(x, y)) {
				// 新建提示框
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.titleProperty().set("游戏结束");
				if (is_black) {
					alert.headerTextProperty().set("白棋获胜");
					history.add("白棋获胜");
				} else {
					alert.headerTextProperty().set("黑棋获胜");
					history.add("黑棋获胜");
				}
				// 保存棋谱
				saveHistory();
				// 弹出提示框
				alert.showAndWait();
			}
		}
	}

	// 判断胜负，返回值为true则说明该新子落下后能连成五子，为false则说明无法连成五子
	public boolean isEnd(int x, int y) {
		int[] arr;
		int center = array[x][y];
		// 判断以新落子为中心的左边四个子与右边四个子共九个子在横向是否能连成五子
		// 使数组全部为0
		arr = new int[9];
		// 将棋盘上相应的数据放入数组
		for (int i = 0; i < 9; i++) {
			if ((x + i - 4 < 0) || (x + i - 4 > MetaData.SIDE_NUMBER - 1)) {
				continue;
			} else {
				arr[i] = array[x + i - 4][y];
			}
		}
		// 判断数组是否能连成五子
		if (isFiveTheSame(arr, center)) {
			return true;
		}
		// 判断纵向是否连成五子
		arr = new int[9];
		for (int i = 0; i < 9; i++) {
			if (y + i - 4 < 0 || y + i - 4 > MetaData.SIDE_NUMBER - 1) {
				continue;
			} else {
				arr[i] = array[x][y + i - 4];
			}
		}
		if (isFiveTheSame(arr, center)) {
			return true;
		}
		// 判断左上右下是否连成五子
		arr = new int[9];
		for (int i = 0; i < 9; i++) {
			if (x + i - 4 < 0 || x + i - 4 > MetaData.SIDE_NUMBER - 1 || y + i - 4 < 0
					|| y + i - 4 > MetaData.SIDE_NUMBER - 1) {
				continue;
			} else {
				arr[i] = array[x + i - 4][y + i - 4];
			}
		}
		if (isFiveTheSame(arr, center)) {
			return true;
		}
		// 判断右上左下是否连成五子
		arr = new int[9];
		for (int i = 0; i < 9; i++) {
			if (x + i - 4 < 0 || x + i - 4 > MetaData.SIDE_NUMBER - 1 || y - (i - 4) < 0
					|| y - (i - 4) > MetaData.SIDE_NUMBER - 1) {
				continue;
			} else {
				arr[i] = array[x + i - 4][y - (i - 4)];
			}
		}
		if (isFiveTheSame(arr, center)) {
			return true;
		}
		return false;
	}

	// 判断一个数组中是否有连续五个符合要求的数字
	public boolean isFiveTheSame(int[] arr, int a) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == a) {
				sum++;
			} else {
				sum = 0;
			}
			if (sum == 5) {
				return true;
			}
		}
		return false;
	}

	// 保存棋谱。一旦开始保存棋谱，说明游戏已经结束，因此同时将鼠标监听事件清空，使得棋盘无法再次落子
	public void saveHistory() {
		// 新建File文件，命名为当前时间
		Date date = new Date();
		String fileName = date.getTime() + ".txt";
		// 创建该文件
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 获取输入流
		try {
			FileWriter fw = new FileWriter(new File(fileName));
			// 获取棋谱的字节并写到文件中
			for (int i = 0; i < this.history.size(); i++) {
				String temp = this.history.get(i);
//				System.out.println(temp);
				byte[] bytes = temp.getBytes();
				fw.write(temp);
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使鼠标监听事件清空，使得棋盘再也无法落子
		this.setOnMouseClicked(null);
	}

	// 悔棋
	public void regret() {
		// 如果数组中的数据太小，说明没有可以退回的棋步，什么都不做直接返回
		if (history.size() < 2) {
			return;
		}
		// 取出最后一步棋子的位置信息
		String theLastFirst = history.remove(history.size() - 1);
		int start1 = theLastFirst.indexOf("(");
		int middle1 = theLastFirst.indexOf(",");
		int end1 = theLastFirst.indexOf(")");
		Integer theLastFirstX = Integer.parseInt(theLastFirst.substring(start1 + 1, middle1));
		Integer theLastFirstY = Integer.parseInt(theLastFirst.substring(middle1 + 1, end1));
		array[theLastFirstX][theLastFirstY] = 0;// 使相应的位置中的数组状态为空
		// 取出倒数第二步棋子的位置信息
		String theLastSecond = history.remove(history.size() - 1);
		int start2 = theLastSecond.indexOf("(");
		int middle2 = theLastSecond.indexOf(",");
		int end2 = theLastSecond.indexOf(")");
		Integer theLastSecondX = Integer.parseInt(theLastSecond.substring(start2 + 1, middle2));
		Integer theLastSecondY = Integer.parseInt(theLastSecond.substring(middle2 + 1, end2));
		array[theLastSecondX][theLastSecondY] = 0;
		// 使棋子消失
		Ellipse ellipse1 = list.remove(list.size() - 1);
		this.getChildren().remove(ellipse1);
		Ellipse ellipse2 = list.remove(list.size() - 1);
		this.getChildren().remove(ellipse2);
	}

}
