package com.olar.Helper;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;


public class S3Bucket {
    private static final String TAG = "S3Bucket";
    private Context mContext;
    private String mFilePath;
    private Uri uri;
    private UploadListener mUploadListener;
    ///private AmazonS3Client amazonS3Client;

    //    public static final String IDENTITY_POOL_ID = "us-east-2:c9d5bd11-b79e-45a6-a49c-fc711d4967d3";
//    public static final String BUCKET_NAME = "streettak";
    public static final String BUCKET_NAME = "vaccinebuddy";
    public static final String FOLDER_PATH = "profile/";
    public static final String IDENTITY_POOL_ID = "us-east-1:e6be5972-ff63-4e43-95a5-4a645d674057";

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String DOC = "doc";
    public static final String AUDIO = "audio";
    private String mFileType = "";

    private ProgressDialog progressDialog;
    private String mMessage = "Uploading Image...";

    //  url type  = https://streettak.s3.us-east-2.amazonaws.com/JPEG_20220310_182202_1685348675.jpg
    private String splitKey = "amazonaws.com/";


    public interface UploadListener {
        void onUploadComplete(String url);

        void onFailure();
    }


    /*public S3Bucket(Context context, String filePath, String fileType, String message, UploadListener uploadListener) {
        this.mContext = context;
        this.mFilePath = filePath;
        this.mUploadListener = uploadListener;

        mFileType = fileType;
        this.mMessage = message;

        initializeAmazonCredentialProvider();

        uplaodFile();
    }

    public S3Bucket(Context context, Uri videoUri, UploadListener uploadListener) {
        this.mContext = context;
        this.uri = videoUri;
        this.mUploadListener = uploadListener;

        mFileType = VIDEO;

        initializeAmazonCredentialProvider();

        compressVideo(videoUri);
    }

    public S3Bucket(Context context) {
        this.mContext = context;

        initializeAmazonCredentialProvider();

    }
*/
   /* private void initializeAmazonCredentialProvider() {
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                mContext,
                IDENTITY_POOL_ID, // Identity pool ID
                Regions.US_EAST_1 // Region
        );

        TransferNetworkLossHandler.getInstance(mContext.getApplicationContext());

        amazonS3Client = new AmazonS3Client(credentialsProvider);
    }


    public void uplaodFile() {
        File file = null;
        if (mFileType.equalsIgnoreCase(IMAGE)) {
            try {
                file = new Compressor(mContext).compressToFile(new File(mFilePath));

                showProgressDialog(mMessage, ProgressDialog.STYLE_SPINNER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (mFileType.equalsIgnoreCase(VIDEO)) {
            file = new File(mFilePath);

            showProgressDialog("Sending Video...", ProgressDialog.STYLE_HORIZONTAL);
        } else if (mFileType.equalsIgnoreCase(AUDIO)) {
            file = new File(mFilePath);

            showProgressDialog("Sending Audio...", ProgressDialog.STYLE_SPINNER);
        } else if (mFileType.equalsIgnoreCase(DOC)) {
            file = new File(mFilePath);
            showProgressDialog("Sending Doc...", ProgressDialog.STYLE_HORIZONTAL);
        }

        if (file == null)
            return;


        TransferObserver transferObserver = new TransferUtility(amazonS3Client, mContext).upload(BUCKET_NAME, file.getName(), file);

        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.equals(TransferState.COMPLETED)) {
                    String url = "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + transferObserver.getKey();
                    dismissProgress();
                    if (mUploadListener != null)
                        mUploadListener.onUploadComplete(url);
                } else {
                    if (mUploadListener != null)
                        mUploadListener.onFailure();
                    dismissProgress();
                }


            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                float percentage = ((float) bytesCurrent / (float) bytesTotal * 100);
                Log.d(TAG, "onProgressChanged: UPLOAD PERCENT => " + percentage);

                progressDialog.setProgress(Math.round(percentage));


            }

            @Override
            public void onError(int id, Exception ex) {
                if (mUploadListener != null)
                    mUploadListener.onFailure();

                dismissProgress();

            }
        });

    }


    private void compressVideo(Uri videoUri) {
        ArrayList<Uri> videoUris = new ArrayList<>();
        videoUris.add(videoUri);

        showProgressDialog("Sending Video...", ProgressDialog.STYLE_SPINNER);


        VideoCompressor.start(
                mContext, // => This is required
                videoUris, // => Source can be provided as content uris
                false, // => isStreamable
                Environment.DIRECTORY_DOWNLOADS, // => the directory to save the compressed video(s)
                new CompressionListener() {
                    @Override
                    public void onSuccess(int i, long l, @Nullable String s) {

                        dismissProgress();
                        if (i == 0) {
                            mFilePath = s;
                            uplaodFile();
                        }


                    }

                    @Override
                    public void onStart(int i) {

                    }


                    @Override
                    public void onFailure(int index, String failureMessage) {
                        // On Failure
                        Log.e(TAG, "onFailure: " + failureMessage);
                        dismissProgress();
                    }

                    @Override
                    public void onProgress(int index, float progressPercent) {
                        // Update UI with progress value

                    }

                    @Override
                    public void onCancelled(int index) {
                        dismissProgress();
                        // On Cancelled
                    }
                }, new Configuration(
                        VideoQuality.LOW,
                        24, *//*frameRate: int, or null*//*
                        false, *//*isMinBitrateCheckEnabled*//*
                        null, *//*videoBitrate: int, or null*//*
                        false, *//*disableAudio: Boolean, or null*//*
                        false, *//*keepOriginalResolution: Boolean, or null*//*
                        360.0, *//*videoWidth: Double, or null*//*
                        480.0 *//*videoHeight: Double, or null*//*


                )
        );
    }*/


    private void showProgressDialog(String message, int style) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(style);
        progressDialog.show();

    }

    private void dismissProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

   /* public void deleteFromS3(String url) {
        String[] parts = url.split(splitKey);

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    amazonS3Client.deleteObject(BUCKET_NAME, parts[1]);
                }
            }).start();

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }

    }
*/
}
