package org.ide.andriod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.apk.builder.FileUtil;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;

public class EditorBridge {

    public static final int RESULT_OPENED = 0;
    public static final int RESULT_ALREADY_OPEN = 1;
    public static final int RESULT_JSON_NOT_FOUND = 2;
    public static final int RESULT_RESERVED_FILE = 3;

    public interface FragmentListener {
        void _onTabsChanged(double _selectIndex);
        void _onFileSaved(String _path);
    }

    private String _projectPath = "";
    private String _projectDataPath = "";
    private String _projectName = "";
    private FragmentListener _fragmentListener;
    private boolean _listenerAttached = false;
    private final Handler _mainHandler = new Handler(Looper.getMainLooper());

    public void _setProjectPath(String _path) {
        _projectPath = _path;
    }

    public String _getProjectPath() {
        return _projectPath;
    }

    public void _setProjectDataPath(String _dataPath) {
        _projectDataPath = _dataPath;
    }

    public String _getProjectDataPath() {
        return _projectDataPath;
    }

    public void _setProjectName(String _name) {
        _projectName = _name;
    }

    public String _getProjectName() {
        return _projectName;
    }

    public void _setFragmentListener(FragmentListener _listener) {
        if (_listenerAttached) return;
        _fragmentListener = _listener;
        _listenerAttached = true;
    }

    public void _clearFragmentListener() {
        _fragmentListener = null;
        _listenerAttached = false;
    }

    private String _jsonPath() {
        return _projectDataPath.concat("/editor/editorOpened.json");
    }

    // reads the tab map fresh from disk every time - name is the key,
    // so an entry structurally cannot exist without a name
    public LinkedHashMap<String, HashMap<String, Object>> _getListMap() {
        try {
            String _json = FileUtil.readFile(_jsonPath());
            LinkedHashMap<String, HashMap<String, Object>> _fromDisk = new Gson().fromJson(
                _json,
                new TypeToken<LinkedHashMap<String, HashMap<String, Object>>>(){}.getType()
            );
            return _fromDisk != null ? _fromDisk : new LinkedHashMap<String, HashMap<String, Object>>();
        } catch (Exception e) {
            return new LinkedHashMap<>();
        }
    }

    // returns the ordered list of tab names, since JSON object key order
    // is preserved by LinkedHashMap on both read and write
    public ArrayList<String> _getTabNames() {
        return new ArrayList<>(_getListMap().keySet());
    }

    private void _writeListMap(LinkedHashMap<String, HashMap<String, Object>> _map) {
        FileUtil.writeFile(_jsonPath(), new Gson().toJson(_map));
    }

    public int _openFile(final String _path) {
        final String _jsonPath = _jsonPath();

        if (_path.equals(_jsonPath)) {
            return RESULT_RESERVED_FILE;
        }

        LinkedHashMap<String, HashMap<String, Object>> _map = _getListMap();

        for (String _key : _map.keySet()) {
            if (_map.get(_key).get("path").toString().equals(_path)) {
                final double _existingIndex = new ArrayList<>(_map.keySet()).indexOf(_key);
                if (_fragmentListener != null) {
                    _mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            _fragmentListener._onTabsChanged(_existingIndex);
                        }
                    });
                }
                return RESULT_ALREADY_OPEN;
            }
        }

        try {
            String _fileName = Uri.parse(_path).getLastPathSegment();
            if (_fileName == null || _fileName.trim().isEmpty()) {
                _fileName = _path;
            }

            // guard against duplicate tab names colliding as keys
            String _finalName = _fileName;
            int _suffix = 1;
            while (_map.containsKey(_finalName)) {
                _suffix++;
                _finalName = _fileName + " (" + _suffix + ")";
            }

            HashMap<String, Object> _entry = new HashMap<>();
            _entry.put("path", _path);
            _map.put(_finalName, _entry);

            final double _newIndex = _map.size() - 1;
            _writeListMap(_map);

            if (_fragmentListener != null) {
                _mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        _fragmentListener._onTabsChanged(_newIndex);
                    }
                });
            }
            return RESULT_OPENED;

        } catch (Exception e) {
            return RESULT_JSON_NOT_FOUND;
        }
    }

    public void _closeTab(int _index) {
        LinkedHashMap<String, HashMap<String, Object>> _map = _getListMap();
        ArrayList<String> _keys = new ArrayList<>(_map.keySet());
        if (_index < 0 || _index >= _keys.size()) return;
        _map.remove(_keys.get(_index));
        _writeListMap(_map);
    }

    public void _notifyFileSaved(String _path) {
        if (_fragmentListener != null) {
            _fragmentListener._onFileSaved(_path);
        }
    }
}