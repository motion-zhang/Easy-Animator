package cs3500.animator.view;


import java.awt.*;
import java.awt.event.ActionListener;

import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.shape.AniShape;


//A hybrid of svgView and VisualView, which implements IHybridView.
//Hybrid view extends the functionality of visual view, and new button panel and
//check box panel is added.
public class HybridView extends VisualView implements IHybridView {
  SvgView svgV;
  int rate;


  protected List<AniShape> shapeList;
  protected JPanel buttonPanel;
  protected JPanel exportPanel;
  protected JButton start;
  protected JButton pause;
  protected JButton resume;
  protected JButton restart;
  protected JButton notLoopBack;
  protected JButton export;
  protected JButton speedUp;
  protected JButton slowDown;
  protected JButton changeColor;
  protected JButton layerButton1;
  protected JButton layerButton2;
  protected JButton layerButton3;
  protected JPanel checkBoxPanel;
  protected JLabel checkBoxDisplay;
  protected JCheckBox[] checkBox;
  protected JScrollPane checkBoxScrollPane;
  protected JLabel exportInfo;
  protected JLayeredPane layeredPane;
  protected JSlider scrubBar;
  private JLabel layerLabel;
  private JTextField exportInput;
  ;


  /**
   * Constructor of HybridView. added functional button AnishapeView and exportTextfield.
   */
  public HybridView(SvgView svgV, int rate) {

    super(svgV.getModel(), rate);
    shapePanel.number = 1;
    layeredPane = new JLayeredPane();
    this.setVisible(false);
    this.shapeList = svgV.getModel().getShapeList();
    checkBox = new JCheckBox[svgV.getModel().getShapeList().size()];

    this.pause();
    this.svgV = svgV;

    this.setTitle("Shape Animation");
    this.setSize(1200, 1200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //***************************************


    // button panel************************************
    buttonPanel = new JPanel();
    //buttonPanel.setLayout(new FlowLayout());
    start = new JButton("start");
    start.setActionCommand("Start Button");
    buttonPanel.add(start);

    pause = new JButton("pause");
    pause.setActionCommand("Pause Button");
    buttonPanel.add(pause);

    resume = new JButton("resume");
    resume.setActionCommand("Resume Button");
    buttonPanel.add(resume);

    restart = new JButton("restart");
    restart.setActionCommand("Restart Button");
    buttonPanel.add(restart);

    speedUp = new JButton("speedUp");
    speedUp.setActionCommand("Speed Up");
    buttonPanel.add(speedUp);

    slowDown = new JButton("slowDown");
    slowDown.setActionCommand("Slow Down");
    buttonPanel.add(slowDown);

    notLoopBack = new JButton("Switch LoopBack");
    notLoopBack.setActionCommand("Not Loop");
    buttonPanel.add(notLoopBack);

    changeColor = new JButton("Change Color");
    changeColor.setActionCommand("change color");
    buttonPanel.add(changeColor);

    layerLabel = new JLabel("Pick front layer");
    buttonPanel.add(layerLabel);

    layerButton1 = new JButton("layer1");
    layerButton1.setActionCommand("layer one");
    buttonPanel.add(layerButton1);

    layerButton2 = new JButton("layer2");
    layerButton2.setActionCommand("layer two");
    buttonPanel.add(layerButton2);

    layerButton3 = new JButton("layer3");
    layerButton3.setActionCommand("layer three");
    buttonPanel.add(layerButton3);

    layerButton1.setVisible(false);
    layerButton2.setVisible(false);
    layerButton3.setVisible(false);

    //************************************
    exportPanel = new JPanel();
    exportInfo = new JLabel("File to export to");
    exportPanel.add(exportInfo);

    exportInput = new JTextField(10);
    exportPanel.add(exportInput);

    export = new JButton("Export");
    export.setActionCommand("Export Button");
    exportPanel.add(export);

    exportPanel.setPreferredSize(new Dimension(200, 200));

    buttonPanel.add(exportPanel);


    //************************************************

    buttonPanel.setPreferredSize(new Dimension(150, 200));

    this.add(new JScrollPane(buttonPanel), BorderLayout.EAST);

    //************************************************
    this.checkBoxPanel = new JPanel();

    checkBoxPanel.setLayout(new GridLayout(5, 8));
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Interactive Buttons"));
    checkBoxScrollPane = new JScrollPane(checkBoxPanel);
    checkBoxDisplay = new JLabel("Please choose the shapes you want to see");
    checkBoxPanel.add(checkBoxDisplay);


    for (int i = 0; i < checkBox.length; i++) {
      checkBox[i] = new JCheckBox(shapeList.get(i).getName());
      checkBox[i].setSelected(true);
      checkBox[i].setActionCommand(shapeList.get(i).getName());
      checkBoxPanel.add(checkBox[i]);
    }


    checkBoxScrollPane = new JScrollPane(checkBoxPanel);
    this.add(checkBoxScrollPane, BorderLayout.PAGE_END);

    //***********************************************
    this.scrubBar = new JSlider(JSlider.HORIZONTAL,1, 100000, 1);
    this.scrubBar.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        shapePanel.time += 50;
      }
    });
    this.add(scrubBar, BorderLayout.PAGE_START);
    //***********************************************

    if (this.svgV.getModel().getLayerNumber() == 2) {
      this.setVisible(true);
      this.layerButton1.setVisible(true);
      this.layerButton2.setVisible(true);
    }
    else if (this.svgV.getModel().getLayerNumber() == 3) {
      this.setVisible(true);
      this.layerButton1.setVisible(true);
      this.layerButton2.setVisible(true);
      this.layerButton3.setVisible(true);
    }
    else {
      this.setVisible(true);
      this.layerButton1.setVisible(true);
    }


  }

  @Override
  public String viewText(boolean ifloopback, Color color) {

    return this.svgV.viewText(true, color);
  }

  @Override
  public void setRate(int rate) {
    this.svgV.setRate(rate);
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    start.addActionListener(actionListener);
    pause.addActionListener(actionListener);
    resume.addActionListener(actionListener);
    restart.addActionListener(actionListener);
    speedUp.addActionListener(actionListener);
    slowDown.addActionListener(actionListener);
    notLoopBack.addActionListener(actionListener);
    export.addActionListener(actionListener);
    changeColor.addActionListener(actionListener);
    layerButton1.addActionListener(actionListener);
    layerButton2.addActionListener(actionListener);
    layerButton3.addActionListener(actionListener);
  }

  @Override
  public void addItemListener(ItemListener itemListener) {

    for (JCheckBox checkBox : checkBox) {
      checkBox.addItemListener(itemListener);
    }
  }

  @Override
  public void setColor(int color) {
    switch (color) {
      case 1:
        this.shapePanel.setBackground(Color.white);

        break;
      case 2:
        this.shapePanel.setBackground(Color.cyan);

        break;
      case 3:
        this.shapePanel.setBackground(Color.pink);

        break;
      case 4:
        this.shapePanel.setBackground(Color.green);

        break;
      case 5:
        this.shapePanel.setBackground(Color.gray);
        break;
      default:
        throw new RuntimeException("No such color");
    }
  }

  @Override
  public void layer1Action() {
    this.layerButton1.setBackground(Color.pink);
    layerButton1.setOpaque(true);
    this.layerButton2.setBackground(null);
    this.layerButton3.setBackground(null);
    shapePanel.number = 1;

  }

  @Override
  public void layer2Action() {
    this.layerButton2.setBackground(Color.pink);
    layerButton2.setOpaque(true);
    this.layerButton1.setBackground(null);
    this.layerButton3.setBackground(null);
    shapePanel.number = 2;

  }

  @Override
  public void layer3Action() {
    this.layerButton3.setBackground(Color.pink);
    layerButton3.setOpaque(true);
    this.layerButton1.setBackground(null);
    this.layerButton2.setBackground(null);
    shapePanel.number = 3;

  }

  @Override
  public void startAnimation() {
    super.timer.start();
  }

  @Override
  public void pauseAnimation() {
    super.timer.stop();
  }

  @Override
  public void resumeAnimation() {
    super.timer.start();
  }

  @Override
  public void restartAnimation() {
    shapePanel.reStart();
  }


  @Override
  public void speedUP() {
    this.rate = this.rate + 10;

    super.timer.setDelay(1000 / rate);
  }

  @Override
  public void slowDwon() {

    if (this.rate <= 10) {
      this.rate = 1;
      super.timer.setDelay(1000 / rate);

    } else {
      this.rate = this.rate - 10;
      super.timer.setDelay(1000 / rate);
    }

    System.out.println("current rate =" + super.getRate());

  }

  @Override
  public void switchLoop() {
    super.loopBack = !loopBack;
  }

  @Override
  public void export() {
    System.out.println("Your input: " + exportInput.getText());


    PrintStream origOut = System.out;
    try {
      PrintStream ps;

      if (exportInput.getText().equals("")) {
        ps = System.out;
      } else {
        ps = new PrintStream(new FileOutputStream(exportInput.getText()));
      }

      System.setOut(ps);

    } catch (FileNotFoundException o) {
      JOptionPane.showMessageDialog(this, "Can't find out put file!");
      return;
    }

    System.out.println(this.svgV.viewText(super.loopBack, shapePanel.getBackground()));
    System.setOut(origOut);


    exportInput.setText("");
  }

  /**
   * pause the animation in the HybridView.
   */
  private void pause() {
    this.timer.stop();
  }

  private int inputLayer() {
    String s = JOptionPane.showInputDialog("How many layers? : ");


    if (s.contains("1")) {
      return 1;
    } else if (s.contains("2")) {
      return 2;
    } else if (s.contains("3")) {
      return 3;
    } else {
      JOptionPane.showMessageDialog(null,
              "Maximum layer of 3, please input again");
      return inputLayer();
    }
  }


  @Override
  public void selectOrUnseletShapes(AniShape shape) {
    if (this.visibleShapes.containsKey(shape.getName())) {
      visibleShapes.remove(shape.getName(), shape);
    } else {
      visibleShapes.put(shape.getName(), shape);
    }
  }



}
