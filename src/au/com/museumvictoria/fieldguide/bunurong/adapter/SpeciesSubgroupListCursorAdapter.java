package au.com.museumvictoria.fieldguide.bunurong.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import au.com.museumvictoria.fieldguide.bunurong.db.FieldGuideDatabase;
import au.com.museumvictoria.fieldguide.bunurong.util.ImageResizer;
import au.com.museumvictoria.fieldguide.bunurong.util.Utilities;
import au.com.museumvictoria.fieldguide.bunurong.R;

/**
 * <p>CursorAdapter for displaying species subgroups</p>
 * 
 * @author Ajay Ranipeta <ajay.ranipeta@gmail.com>
 *
 */
public class SpeciesSubgroupListCursorAdapter extends SimpleCursorAdapter {
	
	private static final String TAG = "SpeciesSubgroupListCursorAdapter";
	
	private Cursor mCursor;
    private LayoutInflater mInflater;
    private Context mContext; 
	
	public SpeciesSubgroupListCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		mCursor = c;
        mInflater = LayoutInflater.from(context);
        mContext = context; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

        if (convertView == null) 
        {
            convertView = mInflater.inflate(R.layout.species_list_groupped, null);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(R.id.speciesLabel);
            holder.text2 = (TextView) convertView.findViewById(R.id.speciesSublabel);
            holder.img =   (ImageView) convertView.findViewById(R.id.speciesIcon);

            holder.sec_hr=(TextView) convertView.findViewById(R.id.speciesSubGroup);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        mCursor.moveToPosition(position);
        String speciesSubgroup = mCursor.getString(mCursor.getColumnIndex(FieldGuideDatabase.SPECIES_SUBGROUP));
        String speciesLabel = mCursor.getString(mCursor.getColumnIndex(FieldGuideDatabase.SPECIES_LABEL));
        String speciesSublabel = mCursor.getString(mCursor.getColumnIndex(FieldGuideDatabase.SPECIES_SUBLABEL));
        String speciesThumbnail = mCursor.getString(mCursor.getColumnIndex(FieldGuideDatabase.SPECIES_THUMBNAIL));
		String iconPath = Utilities.SPECIES_IMAGES_THUMBNAILS_PATH + speciesThumbnail;

        String prevSubgroup = null;

        if (mCursor.getPosition() > 0 && mCursor.moveToPrevious()) {
        	prevSubgroup = mCursor.getString(mCursor.getColumnIndex(FieldGuideDatabase.SPECIES_SUBGROUP));
        	mCursor.moveToNext();
        }


        if(speciesSubgroup.equals(prevSubgroup) || speciesSubgroup.equals("")) {
            holder.sec_hr.setVisibility(View.GONE);
        }
        else {
            holder.sec_hr.setText(speciesSubgroup);
            holder.sec_hr.setVisibility(View.VISIBLE);
        }
        
        holder.text1.setText(speciesLabel);
        holder.text2.setText(speciesSublabel);
        holder.img.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(Utilities.getFullExternalDataPath(mContext, iconPath), 150, 150));

        return convertView;
	}
	
	static class ViewHolder {
        TextView text1;
        TextView text2;
        TextView sec_hr;
        ImageView img;
    }

}
