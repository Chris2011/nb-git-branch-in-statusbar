/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.markiewb.netbeans.plugin.git.showbranch;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.Action;
import javax.swing.SwingWorker;
import org.netbeans.libs.git.GitBranch;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.ModuleInstall;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall implements PropertyChangeListener {

    RequestProcessor requestProcessor = new RequestProcessor(Installer.class);

    @Override
    public void restored() {
        final PropertyChangeListener listenerA = this;
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {
                // code to be invoked when system UI is ready
                TopComponent.getRegistry().addPropertyChangeListener(listenerA);
            }
        });
    }

    @Override
    public void uninstalled() {
        TopComponent.getRegistry().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //run inside EDT, because we are using WindowManager inside
        final FileObject path = new PathUtils().getPath();
        //run the lenghty GIT processing outside EDT
        requestProcessor.execute(new Runnable() {
            @Override
            public void run() {
                final GitBranch activeBranch = GitUtils.getActiveBranch(path);
                if (null != activeBranch) {
                    BranchStatusLineElement.jLabel.setText(activeBranch.getName());
                } else {
                    BranchStatusLineElement.jLabel.setText("");
                }
            }
        });

    }

}
