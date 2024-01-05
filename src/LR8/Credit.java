package LR8;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class Credit extends JFrame {
    private JLabel label1 = new JLabel("Сумма кредита: ");
    private JLabel label2 = new JLabel("Срок кредита: ");
    private JLabel label3 = new JLabel("Процентная ставка: ");
    private JLabel label4 = new JLabel("Тип платежа: ");
    private JLabel label5 = new JLabel("Выплата по кредиту составит: ");
    private JLabel label6 = new JLabel("0");

    String[] items = {"равными долями", "начисление процентов на остаток кредита"};
    private JComboBox comboBox = new JComboBox(items);

    private JButton button1 = new JButton("РАССЧИТАТЬ");

    private JButton button2 = new JButton("Построить график");

    private JTextField textField1 = new JTextField(15);
    private JTextField textField2 = new JTextField(15);
    private JTextField textField3 = new JTextField(15);

    static String value;

    static int time;
    static double[] containerForChart;

    private final String TITLE_message = "Окно сообщения";


    public void init() {
        setTitle("Расчет выплат по кредиту");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);

        add(label1, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(textField1, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(label2, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(textField2, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(label3, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(textField3, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(label4, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(comboBox, new GridBagConstraints(1, 3, 2, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(label5, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(label6, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(button1, new GridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 2, 0, 2), 0, 0));

        add(button2, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 40, 0, 30), 0, 0));

        Font font = new Font("Arial", Font.BOLD, 20);
        textField1.setFont(font);
        textField2.setFont(font);
        textField3.setFont(font);

        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        label4.setFont(font);
        label5.setFont(font);
        label6.setFont(font);

        button1.addActionListener(new Credit.ButtonActionListener1());
        button2.addActionListener(new Credit.ButtonActionListener2());
        comboBox.addActionListener(new Credit.comboBoxActionPerformed());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public class comboBoxActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            value = comboBox.getSelectedItem().toString();
        }
    }

    public class ButtonActionListener1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                double money = Double.parseDouble(textField1.getText());
                time = Integer.parseInt(textField2.getText());
                double bet = Double.parseDouble(textField3.getText());
                bet /= 100;
                double box1, box2 = 0, box3;
                containerForChart = new double[time];
                if (value == "начисление процентов на остаток кредита") {
                    double[] doplata = new double[time];
                    box1 = money / time;
                    for (int i = 0; i < time; i++) {
                        doplata[i] = money * bet * 31 / 365;
                        money -= box1;
                        box2 += box1 + doplata[i];
                        containerForChart[i] = box1 + doplata[i];
                    }
                    label6.setText(String.valueOf(new DecimalFormat("#.##").format(box2)));
                } else {
                    box1 = bet / 12.;
                    box2 = (box1 * Math.pow((1 + box1), time)) / (Math.pow((1 + box1), time) - 1);
                    box3 = money * box2;
                    for (int i = 0; i < time; i++) {
                        containerForChart[i] = box3;
                    }
                    money = box3 * time;
                    label6.setText(String.valueOf(new DecimalFormat("#.##").format(money)));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(Credit.this,
                        "         Заполните поля!", TITLE_message, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ButtonActionListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            initUI();
        }
    }

    private void initUI() {
        JFrame form2 = new JFrame();
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        form2.add(chartPanel);

        form2.pack();
        form2.setTitle("Line chart");
        form2.setLocationRelativeTo(null);
        form2.setVisible(true);
    }

    private XYDataset createDataset() {

        var series = new XYSeries("График выплат");

        if (value == "начисление процентов на остаток кредита") {
            for (int i = 0; i < time; i++) {
                series.add(i + 1, containerForChart[i]);
            }
        } else {
            for (int i = 0; i < time; i++) {
                series.add(i + 1, containerForChart[i]);
            }
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "Месяц",
                "Деньги",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
}