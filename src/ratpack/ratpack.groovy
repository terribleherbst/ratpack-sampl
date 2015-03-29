import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import ratpack.jackson.JacksonModule

import static com.xlson.groovycsv.CsvParser.parseCsv
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json

ratpack {
     bindings {
         add(JacksonModule) {JacksonModule.Config config -> config.modules(new Jdk8Module()).prettyPrint(false)}
     }


    println "Reading all Files from a csv file"
    def csv = new File('starbucks.csv').text
    def data = parseCsv(csv)

    def stores = [:]
    for(line in data) {
        row = [:]
        for (column in line.columns) {
            row.put(column.key,line.propertyMissing(column.key))
        }
        stores.put(row.get("Store_Number"),row)
    }

    handlers {
        get("health") {
            ctx -> response.send("yeah")
        }
        handler("stores") {
            byMethod {
                get {
                    render json(stores.values().take(20))
                }
                post {
                    def store = parse(fromJson())
                    stores.add(store)
                    render json(store)
                }

            }
        }
        handler("stores/:id") {
            def query = pathTokens["id"]
            byMethod {
                get {
                    def output = stores.get(query)
                    println output
                    render json( output)
                }
                put {
                    def store = parse (fromJson())
                    stores.put(query,store)
                }
                delete {
                    stores.remove(query)
                }
            }
        }

        assets "public"
    }
}
