package org.ide.andriod;

import android.content.Context;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectCreator {

	private static final String SHARED_LIBS_DATA_PATH = "/storage/emulated/0/.androIDE/libs/libs_data.json";

	private Context context;
	private String mycDir;
	private String dataDir;
	private String projectId;
	private String projectName;
	private String packageName;
	private String applicationName;
	private String color1;
	private String color2;
	private String color3;
	private String color4;
	private String color5;

	public ProjectCreator(Context _context, String _projectName, String _packageName, String _applicationName, String _color1, String _color2, String _color3, String _color4, String _color5) {
		context = _context;
		projectName = _projectName;
		packageName = _packageName;
		applicationName = _applicationName;
		color1 = _color1;
		color2 = _color2;
		color3 = _color3;
		color4 = _color4;
		color5 = _color5;
		mycDir = "/storage/emulated/0/.androIDE/mysc/";
		dataDir = "/storage/emulated/0/.androIDE/data/";
	}

	public String create(boolean useAppCompat) {
		projectId = ProjectIdGenerator.generate(dataDir);

		String projectMycPath = mycDir.concat(projectName).concat("/");
		String projectDataPath = dataDir.concat(projectName).concat("/");

		new File(projectMycPath).mkdirs();
		new File(projectDataPath).mkdirs();

		copyAssetTemplate(useAppCompat ? "appcompat" : "activity", projectMycPath);
		createSourceFiles(projectMycPath, useAppCompat);
		patchColorsXml(projectMycPath);

		writeMetadata(projectDataPath);
		writePaths(projectDataPath, projectMycPath);
		writeEditorState(projectDataPath, projectMycPath);
		writeLocalLibsJson(projectDataPath);

		return projectId;
	}

	private void createSourceFiles(String projectMycPath, boolean useAppCompat) {
		String mainJava = useAppCompat
			? "package $&#1;\n\nimport android.app.Activity;\nimport android.os.Bundle;\nimport androidx.appcompat.app.AppCompatActivity;\n\npublic class Main extends AppCompatActivity\n{\n    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        \n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.main);\n    }\n    \n}"
			: "package $&#1;\n\nimport android.app.*;\nimport android.os.*;\n\npublic class Main extends Activity \n{\n    @Override\n    protected void onCreate(Bundle savedInstanceState)\n    {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.main);\n    }\n}";

		writeFile(mainJava.replace("$&#1", packageName),
			projectMycPath.concat("app/src/main/java/").concat(packageName.replace(".", "/")).concat("/Main.java"));

		String manifest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<manifest\n	xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    package=\"$&#2\" >\n    <application\n        android:allowBackup=\"true\"\n        android:icon=\"@mipmap/ic_launcher\"\n        android:roundIcon=\"@mipmap/ic_launcher_round\"\n        android:label=\"@string/app_name\"\n        android:theme=\"@style/AppTheme\"\n		android:resizeableActivity = \"true\">\n        <activity\n            android:name=\".Main\"\n            android:label=\"@string/app_name\" >\n            <intent-filter>\n                <action android:name=\"android.intent.action.MAIN\" />\n                <category android:name=\"android.intent.category.LAUNCHER\" />\n            </intent-filter>\n        </activity>\n    </application>\n</manifest>";
		writeFile(manifest.replace("$&#2", packageName), projectMycPath.concat("app/src/main/AndroidManifest.xml"));

		String buildGradle = "apply plugin: 'com.android.application'\n\nandroid {\n    compileSdkVersion 29\n    \n\n    defaultConfig {\n        applicationId \"$&#3\"\n        minSdkVersion 21\n        targetSdkVersion 29\n        versionCode 1\n        versionName \"1.0\"\n    }\n    buildTypes {\n        release {\n            minifyEnabled false\n            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n        }\n    }\n}\n\ndependencies {\n    implementation fileTree(dir: 'libs', include: ['*.jar'])\n}";
		writeFile(buildGradle.replace("$&#3", packageName), projectMycPath.concat("app/build.gradle"));

		String strings = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n\n    <string name=\"app_name\">$&#4</string>\n\n</resources>";
		writeFile(strings.replace("$&#4", applicationName), projectMycPath.concat("app/src/main/res/values/strings.xml"));
	}

	private void patchColorsXml(String projectMycPath) {
		String colorsPath = projectMycPath.concat("app/src/main/res/values/colors.xml");
		File colorsFile = new File(colorsPath);
		if (!colorsFile.exists()) return;

		String content = FileUtil.readFile(colorsPath);
		content = replaceColorTag(content, "colorPrimary", color1);
		content = replaceColorTag(content, "colorPrimaryDark", color2);
		content = replaceColorTag(content, "colorAccent", color3);
		content = replaceColorTag(content, "colorControlHighlight", color4);
		content = replaceColorTag(content, "colorControlNormal", color5);
		writeFile(content, colorsPath);
	}

	private String replaceColorTag(String xml, String name, String hex) {
		String regex = "(<color name=\"" + name + "\">)(.*?)(</color>)";
		return xml.replaceAll(regex, "$1" + hex + "$3");
	}

	private void writeMetadata(String projectDataPath) {
		HashMap<String, Object> metadata = new HashMap<>();
		metadata.put("projectId", projectId);
		metadata.put("projectName", projectName);
		metadata.put("packageName", packageName);
		metadata.put("applicationName", applicationName);
		metadata.put("timestamp", System.currentTimeMillis());
		writeFile(new Gson().toJson(metadata), projectDataPath.concat("metadata.json"));
	}

	private void writePaths(String projectDataPath, String projectMycPath) {
		HashMap<String, Object> paths = new HashMap<>();
		paths.put("root", projectMycPath);
		paths.put("dataRoot", projectDataPath);
		paths.put("appDir", projectMycPath.concat("app/"));
		paths.put("mainJava", projectMycPath.concat("app/src/main/java/").concat(packageName.replace(".", "/")).concat("/Main.java"));
		paths.put("manifest", projectMycPath.concat("app/src/main/AndroidManifest.xml"));
		paths.put("buildGradle", projectMycPath.concat("app/build.gradle"));
		paths.put("stringsXml", projectMycPath.concat("app/src/main/res/values/strings.xml"));
		paths.put("colorsXml", projectMycPath.concat("app/src/main/res/values/colors.xml"));
		paths.put("appConfigJson", projectDataPath.concat("app_config.json"));
		paths.put("editorOpenedJson", projectDataPath.concat("editor/editorOpened.json"));
		paths.put("metadataJson", projectDataPath.concat("metadata.json"));
		paths.put("localLibsJson", projectDataPath.concat("local_libs.json"));
		writeFile(new Gson().toJson(paths), projectDataPath.concat("paths.json"));
	}

	private void writeEditorState(String projectDataPath, String projectMycPath) {
		HashMap<String, Object> proj = new HashMap<>();
		proj.put("path", projectMycPath.concat("app/src/main/java/").concat(packageName.replace(".", "/")).concat("/Main.java"));
		ArrayList<HashMap<String, Object>> editor = new ArrayList<>();
		editor.add(proj);
		writeFile(new Gson().toJson(editor), projectDataPath.concat("editor/editorOpened.json"));

		HashMap<String, Object> appConfig = new HashMap<>();
		appConfig.put("package", packageName);
		appConfig.put("useR8", true);
		appConfig.put("minSdkVersion", 21);
		appConfig.put("targetSdkVersion", 29);
		appConfig.put("versionName", "1.0");
		appConfig.put("versionCode", 1);
		appConfig.put("zipAlignEnabled", true);
		appConfig.put("viewBindingEnabled", true);
		appConfig.put("libraries", "[]");
		writeFile(new Gson().toJson(appConfig), projectDataPath.concat("app_config.json"));
	}

	/**
	 * Writes local_libs.json into the project's data folder.
	 *
	 * Rather than physically copying library files into the project's
	 * app/libs/ folder (the old behavior, now removed along with the bundled
	 * assets/appcompat/app/libs/ tree), this just snapshots every entry
	 * currently registered in the shared libs store
	 * (/storage/emulated/0/.androIDE/libs/libs_data.json) into the project.
	 *
	 * Every shared library is included by default; the user can edit which
	 * ones are attached later via the project's library manager UI, which
	 * should read/write this same local_libs.json file.
	 *
	 * Each entry has the shape: { "lib_name": "...", "lib_path": "..." }
	 * where lib_path points into the shared /storage/emulated/0/.androIDE/libs/
	 * folder, not into the project itself.
	 */
	private void writeLocalLibsJson(String projectDataPath) {
		try {
			if (!FileUtil.isExistFile(SHARED_LIBS_DATA_PATH)) {
				// No shared libs registered yet (e.g. first run hasn't
				// populated /storage/emulated/0/.androIDE/libs/ from assets).
				writeFile("[]", projectDataPath.concat("local_libs.json"));
				return;
			}

			String sharedLibsJson = FileUtil.readFile(SHARED_LIBS_DATA_PATH);
			if (sharedLibsJson == null || sharedLibsJson.trim().isEmpty()) {
				writeFile("[]", projectDataPath.concat("local_libs.json"));
				return;
			}

			// libs_data.json and local_libs.json share the exact same
			// { lib_name, lib_path } array shape, so at creation time this
			// is just a straight copy — no transformation needed.
			writeFile(sharedLibsJson, projectDataPath.concat("local_libs.json"));

		} catch (Exception e) {
			e.printStackTrace();
			// Never let a libs registry problem block project creation.
			writeFile("[]", projectDataPath.concat("local_libs.json"));
		}
	}

	private void copyAssetTemplate(String srcName, String projectMycPath) {
		copyAssetFolder(context, srcName, projectMycPath);
	}

	private void writeFile(String content, String path) {
		FileUtil.writeFile(path, content);
	}

	public static boolean copyAssetFolder(Context context, String srcName, String dstName) {
		try {
			boolean result = true;
			String fileList[] = context.getAssets().list(srcName);
			if (fileList == null) return false;

			if (fileList.length == 0) {
				result = copyAssetFile(context, srcName, dstName);
			} else {
				File file = new File(dstName);
				result = file.mkdirs();
				for (String filename : fileList) {
					result &= copyAssetFolder(context, srcName + File.separator + filename, dstName + File.separator + filename);
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean copyAssetFile(Context context, String srcName, String dstName) {
		try {
			InputStream in = context.getAssets().open(srcName);
			File outFile = new File(dstName);
			OutputStream out = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
