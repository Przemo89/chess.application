angular.module('app.component1').factory('ChallengeStateService', function() {
  'use strict';

   var stateData = {
      form: {},
      isSentChallengesAvailable: false,
      isReceivedChallengesAvailable: false,
      isServerUnavailable: false,
      sentChallenges: [],
      receivedChallenges: [],
      idProfileCurrentPlayer: null,
      playerProfileAndStatistics: []
  };

   return {
     getState: function() {
       return stateData;
     },
     setState: function(data) {
       stateData = data;
     }
   };

});
