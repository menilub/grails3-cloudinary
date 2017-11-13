package grails.plugin.cloudinary

/**
 * Holds the necessary cloudinary account information.
 */
class CloudinaryConfig {

    String apiKey
    String apiSecret
    String cloudName

    @Override
    String toString() {
        "CloudinaryConfig{" +
        "apiKey='" + apiKey + "', " +
        "apiSecret='" + (apiSecret ? '*******' : "") + "', " +
        "cloudName='" + cloudName + "'" +
        "} " + super.toString()
    }
}
