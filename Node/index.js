/*  ==============================================================================
 *
 * Node Example: Use express and node.js to create the initial beginnings of 
 * a basic RESTful service
 *
 * ==============================================================================
 */

/* Express provides a framework that makes handling HTTP
 * request/repsonse sequences simpler
 */
var express = require('express');


// Body Parser - body parser is a convenient piece of "middleware"
// that parses the incoming body of the HTTP request before your route
// handler gets access. The nice thing about this is that we are
// sending JSON data and specifying this in the Content-Type portion
// of the HTTP header and because of this, body parser interprets the
// JSON data and places it into named structures in the req.body
// component. Thus, if you have a element named "name" in your JSON,
// you can access its value with req.body.name.
var bodyParser = require('body-parser');


/* The main instanced class, called app will be initialized by express
 */
var app = express();

/* Set the port in the app system
 */
app.set("port", 4531);

// The next two sections tell bodyParser which content types to
// parse. We are mainly interested in JSON, ut eventually, encoded,
// multipart data may be useful.
app.use(bodyParser.urlencoded({   // support encoded bodies
	    extended: true
		}));
app.use(bodyParser.json());  // support json encoded bodies

var MPGData = {
    MPGArray : []
};

var dataToReturn = {
    "MPG" : '27',
    "prevOdom" :'1000',
    "curOdom" : '1324',
    "gallons" : '12'
};
    
/* -------------------------------------------------
 * ROUTE Descriptions 
 *
 * This is where you process the GET, POST, PUT, DELETE and other
 * potential routes you might need for your API.
 *
 * Default route -- i.e. the Dashboard for you app...
 */
app.get('/', function(request, response) {
	
	/* This call writes the HTTP Response Header status, which
	 * contains the status code (200 in this case) and the "type" of
	 * the body data (HTML in this case)
	 */
	response.writeHead(200, {'Content-Type': 'text/html'});

	/* Each write statement in the response can send text/data into the body 
	 * of the HTTP Response body */
	response.write('<!DOCTYPE html><head><title>DashBoard</title></head><body>');
	response.write('<H1>My App Dashboard</H1>');

	/* You can choose to write other data, such as transforming your internal 
	 * Javascript state into elements you can view on a Dashboard to help you 
	 * debug what's happening. This option may also be a useful tool for your 
	 * clients too since you could tailor the output for an administrative inteface.
	 */
	response.write('JSON Data:');
	response.write(JSON.stringify(dataToReturn));
        /* You could output any JavaScript data here... */

        response.write('</body></html>');

        /* When the body content is completely written, you need to end the write statement
         * which will cause it to be sent on the network. 
         */
        response.end();

        /* Occasional console output can be helpful for debugging too. */
	console.log('Received Dashboard request!');
	MPGData.MPGArray.push(dataToReturn);

    });

/* GET - retrieves data from server */
app.get('/getData', function (request, response) {
	response.writeHead(200,{'Content-Type': 'text/html'});
	response.write(JSON.stringify(MPGData));
	response.end();
	console.log('GET REQUEST: MPG');
    });

/* POST - sends data to server */
app.post('/postData', function (request, response) {

	/* If for some reason, the JSON isn't parsed due to error, return a HTTP ERROR
	 * 400 - in other words, if there is no body
	 */
	if (!request.body) return response.sendStatus(400);

	/* Otherwise, get the data out and do something with it */
	var name = req.body.name;
	var descr = req.body.description;
	var enable = req.body.enable;

	console.log('   name=', name, ', description=', descr, ', enable=', enable);
    });

/* PUT - sends data to server */
app.put('/putData', function (request, response) {
	console.log('PUT REQUEST: putData');
    });

/* DELETE - removes data from server */
app.delete('/deleteData', function (request, response) {
	console.log('DELETE REQUEST: Data has been deleted.');
    });


/* ================================================                          
 * ================================================                          
 * ================================================                          
 * 
 * Error handeling section
 *
 * 404- Missing page handler
 *
 * 500- Broken Page Error
 * ================================================                          
 * ================================================                          
 * ================================================                          
 */

app.use(function(request, response, next){
	request.status(404).send('Sorry, cant find that page!');
    });

app.use(function(error, request, response, next){
	console.error(error.stack);
	request.status(500).send('Something broke!');
    });

/* ================================================
 * ================================================
 * ================================================
 * 
 * FINALLY, start the app and let it listen for connections on the
 * network
 *
 * This really needs to be last.
 *
 * app.listen opens up a network socket on port "port" and waits for
 * HTTP connections
 *
 * ================================================
 * ================================================
 * ================================================
 */


app.listen(app.get("port"), function () {
	console.log('CS4531 Node Example: Node app listening on port: ', app.get("port"));
    });