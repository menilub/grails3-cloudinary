package grails.plugin.cloudinary

import groovy.util.logging.Slf4j

/**
 * Validates the necessary cloudinary account information.
 */
@Slf4j
class CloudinaryConfigValidator {

    static boolean validate(CloudinaryConfig cloudinaryConfig) {
        if (!cloudinaryConfig.apiSecret || !cloudinaryConfig.apiKey || !cloudinaryConfig.cloudName) {
            log.error('Cloudinary config not valid: {}', cloudinaryConfig)
            return false
        }
        true
    }
}
