package grails.plugin.cloudinary

import com.cloudinary.Cloudinary
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j

/**
 * Handles the communication with cloudinary.
 */
@Slf4j
class CloudinaryService {

    CloudinaryConfig cloudinaryConfig
    Cloudinary cloudinary

    Map<String, Object> upload(byte[] imageData, Map<String, Object> options = [:]) {
        cloudinary.uploader().upload(imageData, withCredentials(options))
    }

    Map<String, Object> upload(String imageUrl, Map<String, Object> options = [:]) {
        cloudinary.uploader().upload(imageUrl, withCredentials(options))
    }

    Map<String, Object> upload(File imageFile, Map<String, Object> options = [:]) {
        cloudinary.uploader().upload(imageFile, withCredentials(options))
    }

    Map<String, Object> uploadVideo(byte[] videoData, Map<String, Object> options = [:]) {
        setVideo(options)
        cloudinary.uploader().upload(videoData, withCredentials(options))
    }

    Map<String, Object> uploadVideo(String videoUrl, Map<String, Object> options = [:]) {
        setVideo(options)
        cloudinary.uploader().upload(videoUrl, withCredentials(options))
    }

    Map<String, Object> uploadVideo(File videoFile, Map<String, Object> options = [:]) {
        setVideo(options)
        cloudinary.uploader().upload(videoFile, withCredentials(options))
    }

    void delete(String imageId, Map<String, Object> options = [:]) {
        log.debug("Deleting image from cloudinary: {}", imageId)
        cloudinary.api().deleteResources([imageId], withCredentials(options))
    }

    /////////////////////////////////// internal methods ///////////////////////////////////

    @PackageScope CloudinaryUploadResult uploadImageInternally(Object imageObject, Map<String, Object> options = [:]) {
        log.debug("Uploading image to cloudinary...")
        toCloudinaryUploadResult(cloudinary.uploader().upload(imageObject, withCredentials(options)))
    }

   @PackageScope CloudinaryUploadResult uploadVideoInternally(Object imageObject, Map<String, Object> options = [:]) {
        log.debug("Uploading video to cloudinary...")
        toCloudinaryVideoUploadResult(cloudinary.uploader().upload(imageObject, withCredentials(options)))
    }

    @PackageScope Cloudinary getCloudinary() {
        return new Cloudinary(
                cloud_name: cloudinaryConfig.cloudName,
                api_key: cloudinaryConfig.apiKey,
                api_secrect: cloudinaryConfig.apiSecret
        )
    }

    @PackageScope static CloudinaryUploadResult toCloudinaryUploadResult(Map<String, Object> cloudinaryResult) {
        cloudinaryResult? new CloudinaryUploadResult(
                publicId: cloudinaryResult.public_id,
                version: cloudinaryResult.version as Long,
                signature: cloudinaryResult.signature,
                width: cloudinaryResult.width as Integer,
                height: cloudinaryResult.height as Integer,
                format: cloudinaryResult.format,
                resourceType: cloudinaryResult.resource_type,
                bytes: cloudinaryResult.bytes as Long,
                type: cloudinaryResult.type,
                url: cloudinaryResult.url,
                secureUrl: cloudinaryResult.secure_url,
                createdAt: Date.parse("yyyy-MM-dd'T'HH:mm:ss", String.valueOf(cloudinaryResult.created_at))
        ) : null
    }


   @PackageScope static CloudinaryVideoUploadResult toCloudinaryVideoUploadResult(Map<String, Object> cloudinaryResult) {
        cloudinaryResult ? new CloudinaryVideoUploadResult(
                publicId: cloudinaryResult.public_id,
                version: cloudinaryResult.version as Long,
                signature: cloudinaryResult.signature,
                width: cloudinaryResult.width as Integer,
                height: cloudinaryResult.height as Integer,
                format: cloudinaryResult.format,
                resourceType: cloudinaryResult.resource_type,
                bytes: cloudinaryResult.bytes as Long,
                type: cloudinaryResult.type,
                etag: cloudinaryResult.etag,
                url: cloudinaryResult.url,
                secureUrl: cloudinaryResult.secure_url,
                audioJson: cloudinaryResult.audioJson,
                videoJson: cloudinaryResult.videoJso,
                frameRate: cloudinaryResult.frameRate,
                bitRate: cloudinaryResult.bitRate,
                duration: cloudinaryResult.duration,
                createdAt: Date.parse("yyyy-MM-dd'T'HH:mm:ss", String.valueOf(cloudinaryResult.created_at))
        ) : null
    }

    private Map<String, Object> setVideo(Map<String, Object> options) {
        options.resource_type = 'video'
        return options
    }

    private Map<String, Object> withCredentials(Map<String, Object> options) {
        options.api_key = cloudinaryConfig.apiKey
        options.api_secret = cloudinaryConfig.apiSecret
        options
    }

}