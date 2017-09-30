package com.garudabrowser.www.apps;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nopal on 30/08/2017.
 */
public class LinkHolder {
    private TextView url;

    private ImageView imageView;

    public TextView getUrl()
    {
        return url;
    }

    public void setUrl(TextView url)
    {
        this.url = url;
    }

    public ImageView getImageView()
    {
        return imageView;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }
}
