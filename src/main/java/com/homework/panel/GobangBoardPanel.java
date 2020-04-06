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
	// ��ֱ��壬�����洢�����ϵ����������ڻ����̣��������Ϊ��࣬1����������������ռ�Ŀ�ȣ����Լ����Ҫ��ȥ�ÿ��
	public VBox vBox = new VBox(MetaData.SIDE_WIDTH_UNIT - 1);
	// ˮƽ��壬�����洢�����ϵ����������ڻ����̣��������Ϊ���
	public HBox hBox = new HBox(MetaData.SIDE_WIDTH_UNIT - 1);
	// �Ƿ��ֵ��ڷ����ӣ�trueΪ�ֵ��ڷ����ӣ�falseΪ�ֵ��׷����ӣ�ִ�����У���˳�ʼֵΪtrue
	public boolean is_black = true;
	// ����ÿ��λ�õ��������
	public Integer[][] array = new Integer[MetaData.SIDE_NUMBER][MetaData.SIDE_NUMBER];
	// ���ӵ���ʷ��¼
	public ArrayList<String> history = new ArrayList<String>();
	// �洢�������Ӷ���
	public ArrayList<Ellipse> list = new ArrayList<Ellipse>();

	public GobangBoardPanel() {
		// ������
		drawGobangBoard();
		// ��ʼ����������
		initGobangArray();
		// ��������¼�
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// ����������������������������ӵ�����Ӧ����������
				int x = (int) Math.round(event.getX() / MetaData.SIDE_WIDTH_UNIT);
				int y = (int) Math.round(event.getY() / MetaData.SIDE_WIDTH_UNIT);
				// �ڸ����ӵ��������
				draw(x, y);
			}
		});
	}

	// ��������
	public void drawGobangBoard() {
		// �������̱���ɫ
		this.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
		// ������
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
		// ������
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
		// ����������ӵ�����У��γ�����
		this.getChildren().add(vBox);
		this.getChildren().add(hBox);
	}

	// ���洢���ӵ�����ȫ����ʼ��Ϊ����
	public void initGobangArray() {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = MetaData.STATUS_BLANK;
			}
		}
	}

	// ���ӣ�������Ӧ�����ϵ�����������������
	public void draw(int x, int y) {
		// �ж��������λ���Ƿ񲻴�������
		if (array[x][y] != MetaData.STATUS_BLANK) {
			// ����������ӣ���ʲô������
			return;
		} else {
			// �ж����ɺڷ����ӻ��ǰ׷�����
			// �������������ϻ��������޸����������ϵ���Ӧ����
			Ellipse ellipse = new Ellipse();
			ellipse.setCenterX(x * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setCenterY(y * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setRadiusX(MetaData.RADIUS);
			ellipse.setRadiusY(MetaData.RADIUS);
			if (is_black) {
				ellipse.setFill(Color.BLACK);
				array[x][y] = MetaData.STATUS_BLACK;
				// �����ݱ��浽������
				history.add("����(" + x + "," + y + ");");
			} else {
				ellipse.setFill(Color.WHITE);
				array[x][y] = MetaData.STATUS_WHITE;
				history.add("����(" + x + "," + y + ");");
			}
			// ��������������ӣ�ʹ���ӿɼ�
			this.getChildren().add(ellipse);
			// �����ӷ��������б����ڻ���
			list.add(ellipse);
			// ת�����ӷ�
			is_black = !is_black;
			// �жϴ�ʱ�Ƿ��������ӣ���������������ʾ��Ϸ��������������
			if (isEnd(x, y)) {
				// �½���ʾ��
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.titleProperty().set("��Ϸ����");
				if (is_black) {
					alert.headerTextProperty().set("�����ʤ");
					history.add("�����ʤ");
				} else {
					alert.headerTextProperty().set("�����ʤ");
					history.add("�����ʤ");
				}
				// ��������
				saveHistory();
				// ������ʾ��
				alert.showAndWait();
			}
		}
	}

	// �ж�ʤ��������ֵΪtrue��˵�����������º����������ӣ�Ϊfalse��˵���޷���������
	public boolean isEnd(int x, int y) {
		int[] arr;
		int center = array[x][y];
		// �ж���������Ϊ���ĵ�����ĸ������ұ��ĸ��ӹ��Ÿ����ں����Ƿ�����������
		// ʹ����ȫ��Ϊ0
		arr = new int[9];
		// ����������Ӧ�����ݷ�������
		for (int i = 0; i < 9; i++) {
			if ((x + i - 4 < 0) || (x + i - 4 > MetaData.SIDE_NUMBER - 1)) {
				continue;
			} else {
				arr[i] = array[x + i - 4][y];
			}
		}
		// �ж������Ƿ�����������
		if (isFiveTheSame(arr, center)) {
			return true;
		}
		// �ж������Ƿ���������
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
		// �ж����������Ƿ���������
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
		// �ж����������Ƿ���������
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

	// �ж�һ���������Ƿ��������������Ҫ�������
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

	// �������ס�һ����ʼ�������ף�˵����Ϸ�Ѿ����������ͬʱ���������¼���գ�ʹ�������޷��ٴ�����
	public void saveHistory() {
		// �½�File�ļ�������Ϊ��ǰʱ��
		Date date = new Date();
		String fileName = date.getTime() + ".txt";
		// �������ļ�
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ��ȡ������
		try {
			FileWriter fw = new FileWriter(new File(fileName));
			// ��ȡ���׵��ֽڲ�д���ļ���
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
		// ʹ�������¼���գ�ʹ��������Ҳ�޷�����
		this.setOnMouseClicked(null);
	}

	// ����
	public void regret() {
		// ��������е�����̫С��˵��û�п����˻ص��岽��ʲô������ֱ�ӷ���
		if (history.size() < 2) {
			return;
		}
		// ȡ�����һ�����ӵ�λ����Ϣ
		String theLastFirst = history.remove(history.size() - 1);
		int start1 = theLastFirst.indexOf("(");
		int middle1 = theLastFirst.indexOf(",");
		int end1 = theLastFirst.indexOf(")");
		Integer theLastFirstX = Integer.parseInt(theLastFirst.substring(start1 + 1, middle1));
		Integer theLastFirstY = Integer.parseInt(theLastFirst.substring(middle1 + 1, end1));
		array[theLastFirstX][theLastFirstY] = 0;// ʹ��Ӧ��λ���е�����״̬Ϊ��
		// ȡ�������ڶ������ӵ�λ����Ϣ
		String theLastSecond = history.remove(history.size() - 1);
		int start2 = theLastSecond.indexOf("(");
		int middle2 = theLastSecond.indexOf(",");
		int end2 = theLastSecond.indexOf(")");
		Integer theLastSecondX = Integer.parseInt(theLastSecond.substring(start2 + 1, middle2));
		Integer theLastSecondY = Integer.parseInt(theLastSecond.substring(middle2 + 1, end2));
		array[theLastSecondX][theLastSecondY] = 0;
		// ʹ������ʧ
		Ellipse ellipse1 = list.remove(list.size() - 1);
		this.getChildren().remove(ellipse1);
		Ellipse ellipse2 = list.remove(list.size() - 1);
		this.getChildren().remove(ellipse2);
	}

}
