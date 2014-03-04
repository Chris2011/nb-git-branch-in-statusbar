/* 
 * Copyright 2014 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
