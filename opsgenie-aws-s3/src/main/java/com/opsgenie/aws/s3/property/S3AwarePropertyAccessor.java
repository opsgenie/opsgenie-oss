package com.opsgenie.aws.s3.property;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.StringUtils;
import com.opsgenie.aws.core.property.AwsPropertyAccessors;
import com.opsgenie.core.property.InputStreamAwarePropertyAccessor;
import com.opsgenie.core.property.PropertyAccessor;
import com.opsgenie.core.property.provider.ProfileProvider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * AWS S3 based {@link PropertyAccessor} implementation
 * which searches/loads properties from given S3 bucket/folder.
 *
 * @author serkan
 */
public class S3AwarePropertyAccessor extends InputStreamAwarePropertyAccessor {

    public S3AwarePropertyAccessor(String bucketName, String objectFolder, String objectName) {
        this(bucketName, objectFolder, objectName, ProfileProvider.getProfile());
    }

    public S3AwarePropertyAccessor(String bucketName, String objectFolder, String objectName,
                                   String profileName) {
        super(getS3InputStreams(bucketName, objectFolder, objectName, profileName));
    }

    private static InputStream[] getS3InputStreams(String bucketName, String objectFolder, String objectName,
                                                   String profileName) {
        List<InputStream> inputStreams = new ArrayList<InputStream>(2);
        try {
            inputStreams.add(getS3InputStream(bucketName, objectFolder, objectName));
        } catch (AmazonS3Exception t) {
        }

        if (profileName != null && profileName.length() > 0) {
            try {
                if (objectFolder == null) {
                    objectFolder = "";
                } else {
                    if (objectFolder.length() > 0 && !objectFolder.endsWith("/")) {
                        objectFolder += "/";
                    }
                }
                inputStreams.add(getS3InputStream(bucketName, objectFolder + objectName , objectName));
            } catch (AmazonS3Exception t) {
            }
        }
        return inputStreams.toArray(new InputStream[inputStreams.size()]);
    }

    private static InputStream getS3InputStream(String bucketName, String objectFolder, String objectName) {
        String objectKey;
        if (StringUtils.hasValue(objectFolder)) {
            if (!objectFolder.endsWith("/")) {
                objectFolder += "/";
            }
            objectKey = objectFolder + objectName;
        } else {
            objectKey = objectName;
        }
        AmazonS3Client s3Client = new AmazonS3Client(AwsPropertyAccessors.getDefaultCredentialsProvider());
        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        return s3Object.getObjectContent();
    }

}
