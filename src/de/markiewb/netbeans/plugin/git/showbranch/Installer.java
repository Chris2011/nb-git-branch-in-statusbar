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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.libs.git.GitBranch;
import org.openide.filesystems.FileObject;
import org.openide.modules.ModuleInstall;
import org.openide.util.RequestProcessor;
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
