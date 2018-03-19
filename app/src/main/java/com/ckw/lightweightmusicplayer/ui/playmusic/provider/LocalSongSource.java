package com.ckw.lightweightmusicplayer.ui.playmusic.provider;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;

import com.ckw.lightweightmusicplayer.repository.Song;
import com.ckw.lightweightmusicplayer.utils.MediaUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ckw
 * on 2018/3/19.
 * 提供本地音乐数据
 */

public class LocalSongSource implements SongSource{
    private List<Song> mLocalSong;
    private Context mContext;

    public LocalSongSource(Context context) {
        mContext = context;
        getLocalSongList();
    }

    private void getLocalSongList(){
        mLocalSong = MediaUtils.getAudioList(mContext);
    }


    /*
    * 将本地歌曲转化为MediaMetadataCompat数据
    * 这里可能还有问题，参数待完善
    * */
    private MediaMetadataCompat buildFromLocal(Song song){
        String title = song.getTitle();
        String album = song.getAlbum();
        String artist = song.getArtist();
        int duration = song.getDuration();
        String source = song.getPath();
        String strId = "";
        if(title != null && artist != null){
            strId = title + artist;
        }
        String id = String.valueOf(strId.hashCode());

        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,id)
                .putString(SongSource.CUSTOM_METADATA_TRACK_SOURCE, source)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM,album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,duration)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .build();
    }

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();

        for (int i = 0; i < mLocalSong.size(); i++) {
            tracks.add(buildFromLocal(mLocalSong.get(i)));
        }

        return tracks.iterator();
    }

    @Override
    public ArrayList<MediaMetadataCompat> getLocalList() {
        ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();
        for (int i = 0; i < mLocalSong.size(); i++) {
            tracks.add(buildFromLocal(mLocalSong.get(i)));
        }
        return tracks;
    }
}
