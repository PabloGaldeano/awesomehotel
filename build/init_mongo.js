db.createCollection("paths")

db.paths.update(
    // query
    {
        "_id" : 0
    },

    // update
    {
        "_id" : 0,
        "name": "Hotel",
        "connections" :
        [


        {"to": 1, "distance": 5},
          {"to": 4, "distance": 6}
        ]

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);
db.paths.update(
    // query
    {
        "_id" : 1
    },

    // update
    {
        "_id" : 1,
        "name": "Aquarium",
        "connections" :
        [
          {"to": 2, "distance": 8},
          {"to": 5, "distance": 6},
          {"to": 3, "distance": 3}
        ]

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);
db.paths.update(
    // query
    {
        "_id" : 2
    },

    // update
    {
        "_id" : 2,
        "name": "Zoo",
        "connections" :
        [
          {"to": 3, "distance": 4},
          {"to": 4, "distance": 3}
        ]

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);
db.paths.update(
    // query
    {
        "_id" : 3
    },

    // update
    {
        "_id" : 3,
        "name": "Castle",
        "connections" :
        [
          {"to": 5, "distance": 8},
          {"to": 4, "distance": 2}
        ]

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);
db.paths.update(
    // query
    {
        "_id" : 4
    },

    // update
    {
        "_id" : 4,
        "name": "Old village",
        "connections" :
        [


        {"to": 5, "distance": 9}

        ]

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);
db.paths.update(
    // query
    {
        "_id" : 5
    },

    // update
    {
        "_id" : 5,
        "name": "River",
        "connections" : []

    },

    // options
    {
        "multi" : false,  // update only one document
        "upsert" : true  // insert a new document, if no existing document match the query
    }
);