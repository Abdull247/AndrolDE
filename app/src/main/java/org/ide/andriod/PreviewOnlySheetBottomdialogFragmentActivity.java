package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.*;
import androidx.asynclayoutinflater.*;
import androidx.biometric.*;
import androidx.constraintlayout.core.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.*;
import androidx.core.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.*;
import androidx.customview.*;
import androidx.documentfile.*;
import androidx.drawerlayout.*;
import androidx.fragment.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.legacy.coreui.*;
import androidx.legacy.coreutils.*;
import androidx.lifecycle.*;
import androidx.lifecycle.livedata.*;
import androidx.lifecycle.livedata.core.*;
import androidx.lifecycle.viewmodel.*;
import androidx.loader.*;
import androidx.localbroadcastmanager.*;
import androidx.slidingpanelayout.*;
import androidx.swiperefreshlayout.*;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.CodeView.OnHighlightListener;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.base.*;
import io.github.rosemoe.sora.langs.css3.*;
import io.github.rosemoe.sora.langs.html.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.python.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.langs.universal.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;

public class PreviewOnlySheetBottomdialogFragmentActivity extends BottomSheetDialogFragment {
	
	private String file_path = "";
	private String file_name = "";
	
	private MaterialCardView materialCardView1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private CodeView codeview1;
	private ImageView image_preview;
	private MaterialCardView materialCardView2;
	private TextView textview1;
	private TextView textview2;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.preview_only_sheet_bottomdialog_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		materialCardView1 = _view.findViewById(R.id.materialCardView1);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		codeview1 = _view.findViewById(R.id.codeview1);
		image_preview = _view.findViewById(R.id.image_preview);
		materialCardView2 = _view.findViewById(R.id.materialCardView2);
		textview1 = _view.findViewById(R.id.textview1);
		textview2 = _view.findViewById(R.id.textview2);
	}
	
	private void initializeLogic() {
		if (getArguments() != null) {
			file_path = getArguments().getString("file_path");
		}
		file_name = Uri.parse(file_path).getLastPathSegment();
		textview1.setText(file_name);
		textview2.setText(file_path);
		if (file_name.endsWith("png") || file_name.endsWith("webp")) {
			codeview1.setVisibility(View.GONE);
			image_preview.setVisibility(View.VISIBLE);
			image_preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(file_path, 1024, 1024));
		} else {
			codeview1.setVisibility(View.VISIBLE);
			image_preview.setVisibility(View.GONE);
			if (file_name.endsWith("xml")) {
				codeview1.setLanguage(Language.XML);
			} else {
				if (file_name.endsWith("java")) {
					codeview1.setLanguage(Language.JAVA);
				} else {
					if (file_name.endsWith("kt")) {
						codeview1.setLanguage(Language.KOTLIN);
					} else {
						
					}
				}
			}
			codeview1.setTheme(Theme.DARCULA);
			codeview1.setCode(FileUtil.readFile(file_path));
			codeview1.apply();
		}
	}
	
}