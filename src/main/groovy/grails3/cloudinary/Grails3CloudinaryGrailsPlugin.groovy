package grails3.cloudinary

import grails.plugin.cloudinary.CloudinaryConfig
import grails.plugin.cloudinary.CloudinaryConfigValidator
import grails.plugin.cloudinary.CloudinaryService
import grails.plugins.*

class Grails3CloudinaryGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails 3 Cloudinary Plugin"
    def author = "Meni Lubetkin"
    def authorEmail = "menilub@gmail.com"
    def description = 'Simplifies the usage of the cloudinary service at http://cloudinary.com/ based on initial work created by Simon Buettner'
    def documentation = "https://github.com/menilub/grails3-cloudinary"
    def license = "Apache"
    def issueManagement = [url: 'https://github.com/menilub/grails-cloudinary/issues']
    def scm = [url: 'https://github.com/menilub/grails-cloudinary']

    def profiles = ['web']


    Closure doWithSpring () {
        { ->
            ConfigObject config = application.config.grails.plugin.cloudinary

            cloudinaryConfig(CloudinaryConfig) {
                apiKey = config.apiKey ?: ''
                apiSecret = config.apiSecret ?: ''
                cloudName = config.cloudName ?: ''
            }

            cloudinary(Cloudinary, [cloud_name: config.cloudName ?: ''])

            cloudinaryService(CloudinaryService) {
                cloudinary = ref('cloudinary')
                cloudinaryConfig = ref('cloudinaryConfig')
            }
        }
    }

    def doWithApplicationContext = { ctx ->
        CloudinaryConfigValidator.validate(ctx.cloudinaryConfig)
    }

    def onConfigChange = { event ->
        ConfigObject config = event.application.config.grails.plugin.cloudinary

        // Update the cloudinary configuration
        CloudinaryConfig cloudinaryConfig = event.ctx.cloudinaryConfig
        cloudinaryConfig.with {
            apiKey = config.apiKey ?: ''
            apiSecret = config.apiSecret ?: ''
            cloudName = config.cloudName ?: ''
        }

        CloudinaryConfigValidator.validate(cloudinaryConfig)

        // Update the cloudinary object
        event.ctx.cloudinary.setConfig('cloud_name', cloudinaryConfig.cloudName)
    }
    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }


    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
