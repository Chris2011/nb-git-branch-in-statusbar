/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugin.git.showbranch;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JLabel;
import org.openide.awt.StatusLineElementProvider;
import org.openide.filesystems.FileUtil;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author markiewb
 */
@ServiceProvider(service = StatusLineElementProvider.class)
public final class BranchStatusLineElement implements StatusLineElementProvider {

    final static JLabel jLabel;
    
    static {
            jLabel = new JLabel("");
            jLabel.setBorder(null);
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

    @Override
    public Component getStatusLineElement() {
        return jLabel;
    }

}
