package arouter.cuiqiang.com.baselib.component;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import arouter.cuiqiang.com.baselib.R;


public class BaseProgressDialog extends BaseDialogFragment {
    private static final String TAG = "BaseProgressDialog";
    private TextView mTitleTv;
    private TextView mDescTv;
    private String mTitle;
    private String mDesc;
    private boolean mIsCancel;
    private View mMainView;
    public static final String PROGRESSDIALOG_TITLE = "progress_dialog_title";
    public static final String PROGRESSDIALOG_DESC = "progress_dialog_desc";
    public static final String PROGRESSDIALOG_OUTSIDE_CANCEL = "progress_dialog_outside_cancel";


    public BaseProgressDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setCanceledOnTouchOutside(mIsCancel);
        Window window = d.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.CENTER);
        setCancelable(true);
        return d;
    }

    private void initParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(PROGRESSDIALOG_TITLE);
            mDesc = bundle.getString(PROGRESSDIALOG_DESC);
            mIsCancel = bundle.getBoolean(PROGRESSDIALOG_OUTSIDE_CANCEL);
        }
    }

    @Override
    protected int[] getDialogSize() {
        return new int[]{WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT};
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_common_layout, container, true);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTitleTv = (TextView) view.findViewById(R.id.title);
        mDescTv = (TextView) view.findViewById(R.id.desc);
        mMainView = view.findViewById(R.id.main_view);
        mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseProgressDialog.this.dismiss();
            }
        });
        mTitleTv.setText(mTitle);
        mDescTv.setText(mDesc);
    }

}
