/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugin.git.showbranch;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.openide.awt.StatusLineElementProvider;
import org.openide.filesystems.FileUtil;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author markiewb
 */
@ServiceProvider(service = StatusLineElementProvider.class)
public final class BranchStatusLineElement implements StatusLineElementProvider {

    static JComponent comp;
    static JLabel jLabel;

    @Override
    public Component getStatusLineElement() {
        if (null == comp) {
            comp = new JPanel(new FlowLayout(FlowLayout.LEFT));
            comp.setBorder(null);
            comp.add(new JSeparator());
            jLabel = new JLabel("");
            jLabel.setBorder(null);
            comp.add(jLabel);
            jLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    Action action = FileUtil.getConfigObject("Actions/Git/org-netbeans-modules-git-ui-checkout-SwitchBranchAction.instance", Action.class);
                    if (null != action) {
                        ActionEvent actionEvent = null;
                        action.actionPerformed(actionEvent);
                    }
                }
            });
        }

        return comp;
    }

}
