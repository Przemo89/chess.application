angular.module('app.component1').factory('ChallengeService', ['$http', function ($http) {
  'use strict';

  return {
    getSentChallenges: function(idPlayer) {
      var url = 'http://localhost:8090/chess/challenge/sentChallenges/' + idPlayer;
      return $http.get(url, {cache: false});
    },
    getReceivedChallenges: function(idPlayer) {
      var url = 'http://localhost:8090/chess/challenge/receivedChallenges/' + idPlayer;
      return $http.get(url, {cache: false});
    },
    acceptChallenge: function(challengeReceivedTO) {
      var acceptURL = 'http://localhost:8090/chess/challenge/accept/';
      return $http.post(acceptURL, challengeReceivedTO);
    },
    declineChallenge: function(challengeReceivedTO) {
      var declineURL = 'http://localhost:8090/chess/challenge/decline/';
      return $http.post(declineURL, challengeReceivedTO);
    },
    cancelChallenge: function(challengeSentTO) {
      var declineURL = 'http://localhost:8090/chess/challenge/cancel/';
      return $http.post(declineURL, challengeSentTO);
    },
    getPlayersMatchingForAutomaticChallenge: function(idPlayer) {
      var playerMatchingURL = 'http://localhost:8090/chess/challenge/automatic/getMatchingPlayers/' + idPlayer;
      return $http.get(playerMatchingURL, {cache: false});
    },
    createChallenge: function(idPlayerStatisticsChallengingToCreateChallenge, idPlayerStatisticsChallengedToCreateChallenge) {
      var createChallengeURL, ChallengeToBeCreatedTO;
      createChallengeURL = 'http://localhost:8090/chess/challenge/manual/create/';
      ChallengeToBeCreatedTO = {
        idPlayerStatisticsChallenging: idPlayerStatisticsChallengingToCreateChallenge,
	      idPlayerStatisticsChallenged: idPlayerStatisticsChallengedToCreateChallenge
      };
      return $http.post(createChallengeURL, ChallengeToBeCreatedTO);
    }
  }
}]);
