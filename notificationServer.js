const functions = require('firebase-functions');

var admin =require("firebase-admin");
var serviceAccount = require("./serviceAccountKey.json")
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://testbehaviour-6a6aa.firebaseio.com/"
  });
  defaultAuth = admin.auth();
  // var ref= admin.database().ref('/users');

  // ref.on("child_added", function(snapshot) {
  //   var uid = snapshot.val();
  //   if(uid.type==="Student" && uid.token!== undefined )
  //     console.log(uid.token);
  // });
  var database = admin.database();

  exports.notifyStudenct=functions.database.ref('/jobs/{jobID}/details')
  .onWrite(event=>{
   
    var snapshot= event.data;
    // var payload={
    //     data:{
    //       type:snapshot.child('jobType').val(),
    //       vacancy:snapshot.child('jobVacancy').val()
    //     }
    // }; 
    var payload={
      data:{
        type:'name',
        vacancy:'3'
      }
  }; 
    var dref = admin.database().ref("/users");
    dref.on("child_added", function(datasnapshot) {
        var uid = datasnapshot.val();
        if(uid.type==="Student" && uid.token!== undefined ){

          var deviceId=uid.token;
          var idd='evAD8lxW2Ok:APA91bE9Jp5ZmS6LiCQYELxu298r217pegiCE6udH56PvyF1ciEFqSSZW69IrJv6MIx57MQTlSUthdfXNpvk7RUlsok72aiou8SLI56xAAasrhuJppTtJfr2XkXlYxt1UlXfil13UWGN';
          return admin.messaging().sendToDevice(idd, payload)
          .then(function (response) {
              console.log("Successfully sent message:", response);
              console.log("Successfully error:", response.results[0].error);
          })
          .catch(function (error) {
              console.log("Error sending message:", error);
          });
        }
    });

  });
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
