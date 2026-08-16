package org.ide.andriod;

import java.util.HashMap;

public class GitBridge {

    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_NOT_A_REPO = 1;
    public static final int RESULT_ERROR = 2;
    public static final int RESULT_NO_CHANGES = 3;

    private String _projectPath = "";
    private org.eclipse.jgit.api.Git _git;

    public void _setProjectPath(String _path) {
        _projectPath = _path;
    }

    public boolean _isRepo() {
        return new java.io.File(_projectPath, ".git").exists();
    }

    public int _init() {
        try {
            _git = org.eclipse.jgit.api.Git.init().setDirectory(new java.io.File(_projectPath)).call();
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    private int _open() {
        try {
            if (_git != null) return RESULT_SUCCESS;
            if (!_isRepo()) return RESULT_NOT_A_REPO;
            _git = org.eclipse.jgit.api.Git.open(new java.io.File(_projectPath));
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public org.eclipse.jgit.api.Status _status() {
        try {
            if (_open() != RESULT_SUCCESS) return null;
            return _git.status().call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int _commit(String _message) {
	try {
		if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
		org.eclipse.jgit.api.Status _s = _git.status().call();
		boolean _hasStagedChanges = !_s.getAdded().isEmpty() || !_s.getChanged().isEmpty() || !_s.getRemoved().isEmpty();
		if (!_hasStagedChanges) {
			return RESULT_NO_CHANGES;
		}
		_git.commit().setMessage(_message).call();
		return RESULT_SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return RESULT_ERROR;
	}
}

    public int _setRemote(String _remoteUrl) {
	try {
		if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
		java.util.List<org.eclipse.jgit.transport.RemoteConfig> _remotes = _git.remoteList().call();
		boolean _exists = false;
		for (org.eclipse.jgit.transport.RemoteConfig _r : _remotes) {
			if (_r.getName().equals("origin")) {
				_exists = true;
				break;
			}
		}
		if (_exists) {
			_git.remoteRemove().setRemoteName("origin").call();
		}
		org.eclipse.jgit.lib.StoredConfig _config = _git.getRepository().getConfig();
		_config.setString("remote", "origin", "url", _remoteUrl);
		_config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
		_config.save();
		return RESULT_SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return RESULT_ERROR;
	}
}

public int _fetch(String _username, String _token) {
	try {
		if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
		_git.fetch()
			.setCredentialsProvider(new org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider(_username, _token))
			.call();
		return RESULT_SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return RESULT_ERROR;
	}
}

public int _pull(String _username, String _token, String _branch) {
	try {
		if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
		_git.pull()
			.setCredentialsProvider(new org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider(_username, _token))
			.setRemoteBranchName(_branch)
			.call();
		return RESULT_SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return RESULT_ERROR;
	}
}

public int _push(String _username, String _token, String _branch) {
	try {
		if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
		_git.push()
			.setCredentialsProvider(new org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider(_username, _token))
			.setRemote("origin")
			.add(_branch)
			.call();
		return RESULT_SUCCESS;
	} catch (Exception e) {
		e.printStackTrace();
		return RESULT_ERROR;
	}
}

    public int _clone(String _remoteUrl, String _username, String _token) {
        try {
            org.eclipse.jgit.api.CloneCommand _cmd = org.eclipse.jgit.api.Git.cloneRepository()
                .setURI(_remoteUrl)
                .setDirectory(new java.io.File(_projectPath));
            if (_username != null && _token != null) {
                _cmd.setCredentialsProvider(new org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider(_username, _token));
            }
            _git = _cmd.call();
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public int _stageFile(String _relativePath, boolean _isDeleted) {
        try {
            if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
            if (_isDeleted) {
                _git.rm().addFilepattern(_relativePath).call();
            } else {
                _git.add().addFilepattern(_relativePath).call();
            }
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public int _unstageFile(String _relativePath) {
        try {
            if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
            _git.reset().addPath(_relativePath).call();
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public int _stageAll() {
        try {
            if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
            _git.add().addFilepattern(".").call();
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public int _unstageAll() {
        try {
            if (_open() != RESULT_SUCCESS) return RESULT_ERROR;
            _git.reset().call();
            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }
    
    public java.util.List<org.eclipse.jgit.revwalk.RevCommit> _log() {
	try {
		if (_open() != RESULT_SUCCESS) return new java.util.ArrayList<>();
		java.util.List<org.eclipse.jgit.revwalk.RevCommit> _commits = new java.util.ArrayList<>();
		for (org.eclipse.jgit.revwalk.RevCommit _c : _git.log().call()) {
			_commits.add(_c);
		}
		return _commits;
	} catch (Exception e) {
		e.printStackTrace();
		return new java.util.ArrayList<>();
	}
}

public String _getHeadCommitId() {
	try {
		if (_open() != RESULT_SUCCESS) return "";
		org.eclipse.jgit.lib.Repository _repo = _git.getRepository();
		org.eclipse.jgit.lib.ObjectId _head = _repo.resolve("HEAD");
		return _head != null ? _head.getName() : "";
	} catch (Exception e) {
		e.printStackTrace();
		return "";
	}
}

public java.util.List<HashMap<String, Object>> _getFileDiff(String _relativePath) {
	java.util.List<HashMap<String, Object>> _rows = new java.util.ArrayList<>();
	try {
		if (_open() != RESULT_SUCCESS) return _rows;

		org.eclipse.jgit.lib.Repository _repo = _git.getRepository();

		// old side: HEAD version of the file
		byte[] _oldBytes = new byte[0];
		try {
			org.eclipse.jgit.lib.ObjectId _headId = _repo.resolve("HEAD");
			if (_headId != null) {
				org.eclipse.jgit.revwalk.RevWalk _walk = new org.eclipse.jgit.revwalk.RevWalk(_repo);
				org.eclipse.jgit.revwalk.RevCommit _commit = _walk.parseCommit(_headId);
				org.eclipse.jgit.revwalk.RevTree _tree = _commit.getTree();
				org.eclipse.jgit.treewalk.TreeWalk _treeWalk = org.eclipse.jgit.treewalk.TreeWalk.forPath(_repo, _relativePath, _tree);
				if (_treeWalk != null) {
					org.eclipse.jgit.lib.ObjectId _blobId = _treeWalk.getObjectId(0);
					_oldBytes = _repo.open(_blobId).getBytes();
				}
				_walk.close();
			}
		} catch (Exception _ignored) {
			// file may be new (no HEAD version) — _oldBytes stays empty
		}

		// new side: current working tree file
		java.io.File _workingFile = new java.io.File(_projectPath, _relativePath);
		byte[] _newBytes = _workingFile.exists()
			? org.eclipse.jgit.util.IO.readFully(_workingFile)
			: new byte[0];

		org.eclipse.jgit.diff.RawText _oldText = new org.eclipse.jgit.diff.RawText(_oldBytes);
		org.eclipse.jgit.diff.RawText _newText = new org.eclipse.jgit.diff.RawText(_newBytes);

		org.eclipse.jgit.diff.EditList _edits = new org.eclipse.jgit.diff.EditList();
		_edits.addAll(new org.eclipse.jgit.diff.HistogramDiff().diff(
			org.eclipse.jgit.diff.RawTextComparator.DEFAULT, _oldText, _newText));

		int _oldLine = 0;
		int _newLine = 0;

		for (org.eclipse.jgit.diff.Edit _edit : _edits) {
			// unchanged context before this edit
			while (_oldLine < _edit.getBeginA() && _newLine < _edit.getBeginB()) {
				_rows.add(_row("context", _oldText.getString(_oldLine), _oldLine + 1, _newLine + 1));
				_oldLine++;
				_newLine++;
			}
			for (int _i = _edit.getBeginA(); _i < _edit.getEndA(); _i++) {
				_rows.add(_row("removed", _oldText.getString(_i), _i + 1, -1));
			}
			for (int _i = _edit.getBeginB(); _i < _edit.getEndB(); _i++) {
				_rows.add(_row("added", _newText.getString(_i), -1, _i + 1));
			}
			_oldLine = _edit.getEndA();
			_newLine = _edit.getEndB();
		}

		// trailing unchanged context after the last edit
		while (_oldLine < _oldText.size() && _newLine < _newText.size()) {
			_rows.add(_row("context", _oldText.getString(_oldLine), _oldLine + 1, _newLine + 1));
			_oldLine++;
			_newLine++;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return _rows;
}

private HashMap<String, Object> _row(String _type, String _content, int _oldLineNum, int _newLineNum) {
	HashMap<String, Object> _row = new HashMap<>();
	_row.put("type", _type);
	_row.put("content", _content);
	_row.put("oldLineNum", _oldLineNum > 0 ? String.valueOf(_oldLineNum) : "");
	_row.put("newLineNum", _newLineNum > 0 ? String.valueOf(_newLineNum) : "");
	return _row;
}

}