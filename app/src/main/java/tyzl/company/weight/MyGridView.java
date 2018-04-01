package tyzl.company.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Admin on 2016/2/24.
 */
public class MyGridView extends GridView {

	public MyGridView(Context paramContext) {
		super(paramContext);
	}

	public MyGridView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MyGridView(Context paramContext, AttributeSet paramAttributeSet,
					  int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}