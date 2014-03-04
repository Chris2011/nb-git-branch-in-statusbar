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

import java.util.Set;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataShadow;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author markiewb
 */
class PathUtils {

    public FileObject getPath() {

        final FileObject fileObjectOfEditor = getFileObject(getCurrentEditor());
        if (fileObjectOfEditor != null) {
            return fileObjectOfEditor;
        }
        final FileObject fileObjectOfNode = getFileObject(TopComponent.getRegistry().getActivated());
        if (fileObjectOfNode != null) {
            return fileObjectOfNode;
        }

        return null;

    }

    /**
     * Returns the original string if not empty or not null. Else return the
     * given default.
     */
    private String defaultIfEmpty(String string, String defaultStr) {
        if (isEmpty(string)) {
            return defaultStr;
        }
        return string;
    }

    private FileObject getFileObjectWithShadowSupport(DataObject dataObject) {
        if (dataObject instanceof DataShadow) {
            DataShadow dataShadow = (DataShadow) dataObject;
            return dataShadow.getOriginal().getPrimaryFile();
        }
        return dataObject.getPrimaryFile();
    }

    /**
     * Gets the currently opened editor.
     */
    private TopComponent getCurrentEditor() {
        Set<? extends Mode> modes = WindowManager.getDefault().getModes();
        for (Mode mode : modes) {
            if ("editor".equals(mode.getName())) {
                return mode.getSelectedTopComponent();
            }
        }
        return null;
    }

    private boolean isEmpty(String string) {
        return null == string || "".equals(string);
    }

    private FileObject getFileObject(TopComponent activeTC) {
        if (null == activeTC) {
            return null;
        }

        //fallback hierarchy: Project, then DataObject, then FileObject
        Project project = activeTC.getLookup().lookup(Project.class);
        if (project!=null && project.getProjectDirectory() != null) {
            return project.getProjectDirectory();
        }
        
        DataObject dataObject = activeTC.getLookup().lookup(DataObject.class);
        if (dataObject != null && getFileObjectWithShadowSupport(dataObject) != null) {
            return getFileObjectWithShadowSupport(dataObject);
        }
        
        FileObject fileObject = activeTC.getLookup().lookup(FileObject.class);
        if (fileObject != null) {
            return fileObject;
        }
        return null;
    }
}
