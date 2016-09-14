exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    baseUrl: 'http://localhost:9000/',
    specs: ['e2e/*e2e.js'],

    capabilities: {
        browserName: 'chrome'
    },

    jasmineNodeOpts: {
        showColors: true,
        isVerbose: true
    }
};
