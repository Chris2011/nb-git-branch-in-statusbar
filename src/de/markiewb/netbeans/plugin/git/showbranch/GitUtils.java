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

import java.io.File;
import java.util.Collection;
import org.netbeans.libs.git.GitBranch;
import org.netbeans.libs.git.GitClient;
import org.netbeans.libs.git.GitException;
import org.netbeans.libs.git.GitRepository;
import org.netbeans.libs.git.progress.ProgressMonitor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author markiewb
 */
public class GitUtils {

    public static GitBranch getActiveBranch(final FileObject f) {
        final GitRepository repo = getGitRepo(f);
        if (null == repo) {
            return null;
        }
        GitClient client = null;
        try {
            client = repo.createClient();
            ProgressMonitor progressMonitor = new ProgressMonitor.DefaultProgressMonitor();
            final Collection<GitBranch> localBranches = client.getBranches(false, progressMonitor).values();
            for (GitBranch branch : localBranches) {
                if (branch.isActive()) {
                    return branch;
                }
            }
        } catch (GitException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            if (null != client) {
                client.release();
            }
        }
        return null;
    }

    static GitRepository getGitRepo(final FileObject f) {
        final FileObject gitRepoDirectory = getGitRepoDirectory(f);
        if (null == gitRepoDirectory) {
            return null;
        }
        final File toFile = FileUtil.toFile(gitRepoDirectory);
        if (null == toFile) {
            return null;
        }
        return GitRepository.getInstance(toFile);
    }

    static FileObject getGitRepoDirectory(FileObject file) {
        FileObject currentFile = file;
        while (currentFile != null) {
            if (currentFile.isFolder() && currentFile.getFileObject(".git", "") != null) {
                return currentFile;
            }
            currentFile = currentFile.getParent();
        }
        return null;
    }

}
