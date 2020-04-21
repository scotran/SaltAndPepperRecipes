import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScalePanel extends JPanel implements ActionListener {
    private JLabel panelLabel, typeLabel, amountLabel;
    private JComboBox<String> typeBox;
    private JTextField amountField;
    private JButton cancelButton, confirmButton;

    private GridBagConstraints gc = new GridBagConstraints();

    private ScaleRecipeListener buttonListener;

    public ScalePanel() {
        setLayout(new GridBagLayout());

        panelLabel = new JLabel("Scale a Recipe");
        typeLabel = new JLabel("Scale up (multiply) or down (divide) ingredients?");
        amountLabel = new JLabel("How much do you want to scale the recipe?");

        typeBox = new JComboBox<String>();

        typeBox.addItem("Up");
        typeBox.addItem("Down");

        amountField = new JTextField(20);

        cancelButton = new JButton("Cancel");
        confirmButton = new JButton("Confirm");

        cancelButton.addActionListener(this);
        confirmButton.addActionListener(this);
        ////////GUI

        gc.insets = new Insets(0, 20, 20, 40);

        //Panel Label
        gc.weightx = 2;
        gc.weighty = 2;
        gc.gridwidth = 5;
        gc.gridx = 0;
        gc.gridy = 0;

        add(panelLabel, gc);

        //Column 1
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;

        add(typeLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        add(amountLabel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.fill = GridBagConstraints.BOTH;

        add(cancelButton, gc);

        //Column 2
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 4;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;

        add(typeBox, gc);

        gc.gridx = 1;
        gc.gridy = 2;

        add(amountField, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.fill = GridBagConstraints.BOTH;

        add(confirmButton, gc);
    }

    public void setButtonListener(ScaleRecipeListener buttonListener) { this.buttonListener = buttonListener; }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();

        if(clicked == confirmButton) {
            if(buttonListener != null) {
                int amount;
                String amountStr = amountField.getText();
                String typeStr = typeBox.getSelectedItem().toString();
                boolean scaleUp;

                if (typeStr.equals("Up"))
                    scaleUp = true;
                else
                    scaleUp = false;

                if (amountStr == null) {
                    String text = "There is no amount inputed";
                    JOptionPane.showMessageDialog(null, text, text, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    amount = Integer.parseInt(amountStr);
                } catch (Exception ex) {
                    String text = "Amount inputed is not valid; make sure it is an integer.";
                    JOptionPane.showMessageDialog(null, text, text, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                buttonListener.scaleRecipe(true, scaleUp, amount);
            }
        }
        else if(clicked == cancelButton) {
            if(buttonListener != null) {
                buttonListener.scaleRecipe(false, true, 0);
            }
        }
    }
}
