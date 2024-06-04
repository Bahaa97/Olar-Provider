package com.olar.Helper;


public class DynamicLinks {
//    private static final String TAG = "FirebaseDynamicLinks";
//    public static final String DOMAIN_PREFIX ="https://microgravity.page.link";
//    public static final String WEBSITE_PREFIX="https://www.microgravity.co.in/";
//    public static final String IOS_BUNDLE_ID="com.mobulous.StreetTalk";
//    //public static final String WEBSITE_PREFIX=" https://streettak.com/#/street-talk-post/";
//
//    public static void createFirebaseDynamicLink(Activity activity, String productInfo, String title, String description, String imageUrl){
//
//        String URL_LINK = WEBSITE_PREFIX +"#/" +productInfo;
//
//        DynamicLink.SocialMetaTagParameters socialMetaTagParameters;
//        if (ValidationUtils.stringNullValidation(imageUrl)){
//            socialMetaTagParameters = new DynamicLink.SocialMetaTagParameters.Builder()
//                    .setTitle(title)
//                    .setDescription(description)
//                    .setImageUrl(Uri.parse(imageUrl))
//                    .build();
//        }else {
//            socialMetaTagParameters = new DynamicLink.SocialMetaTagParameters.Builder()
//                    .setTitle(title)
//                    .setDescription(description)
//                    .build();
//        }
//
//
//        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLink(Uri.parse(URL_LINK))
//                .setDomainUriPrefix(DOMAIN_PREFIX)
//
//                // Open links with this app on Android
//                // .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                .setSocialMetaTagParameters(socialMetaTagParameters)
//                // Open links with com.example.ios on iOS
//                .setIosParameters(new DynamicLink.IosParameters.Builder(IOS_BUNDLE_ID).build())
//                .buildDynamicLink();
//
//        Uri dynamicLinkUri = dynamicLink.getUri();
//
//        Log.d("MyLink Long", "getFirebaseDynamicLink: "+dynamicLinkUri.toString());
//
//        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLongLink(dynamicLinkUri)
//                .buildShortDynamicLink()
//                .addOnCompleteListener(activity, new OnCompleteListener<ShortDynamicLink>() {
//                    @Override
//                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
//                        if (task.isSuccessful()) {
//                            //  dialogPopup.dismissLoadingDialog();
//                            Uri shortLink = task.getResult().getShortLink();
//                            Uri flowchartLink = task.getResult().getPreviewLink();
//                            Log.d("MyLink", "onComplete: "+shortLink.toString());
//                            shareIntent(activity,shortLink.toString() +"/#/"+productInfo);
//                        } else {
//                            Log.d("MyLink Error", "onComplete: "+task.getException());
//                        }
//                    }
//                });
//
//    }
//
//
//    public static void shareIntent(Activity activity, String shearableLink) {
//        ShareCompat.IntentBuilder
//                .from(activity)
//                .setText(shearableLink)
//                .setType("text/plain")
//                .setChooserTitle("Share with the users")
//                .startChooser();
//    }
//

}