import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import groovy.transform.ToString
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json

@ToString(includeNames=true)
class Talk {
    Integer id;
    String title;
    String speaker;
    String company;
    String track;
    String language;
}

ratpack {
     bindings {
         add(JacksonModule) {JacksonModule.Config config -> config.modules(new Jdk8Module()).prettyPrint(false)}
     }

    courses = [
            new Talk(
                    id : 1,
                    title: "Lambdas and Streams, Functional Programming in JDK8",
                    speaker: "Simon Ritter",
                    company: "Oracle",
                    language: "en",
                    track: "Core Java"),
            new Talk(
                    id : 2,
                    title: "Understanding Java byte code",
                    speaker: "Rafael Winterhalter",
                    company: "Kantega AS",
                    language: "en",
                    track: "Core Java"),
            new Talk(
                    id: 3,
                    title: "Legacy",
                    speaker: "Peter Lawrey",
                    company: "Higher Frequency Trading Ltd.",
                    language: "en",
                    track: "Core Java"),
            new Talk(
                    id: 4,
                    title: "Lessons",
                    speaker: "Aron Gupta",
                    company: "Red Hat Inc",
                    language: "en",
                    track: "Enterprise Java"),
            new Talk(
                    id: 5,
                    title: "JBatch mit Apache BatchEE",
                    speaker: "Mark Strutberg",
                    company: "TU Wien",
                    language: "de",
                    track: "Enterprise Java"),
            new Talk(
                    id: 6,
                    title: "Where are we with MVC in JavaEE 8",
                    speaker: "Manfred Riem",
                    company: "Oracle",
                    language: "en",
                    track: "Enterprise Java"),
            new Talk(
                    id: 7,
                    title: "2000 Zeilen Java Code oder 50 Zeilen SQL",
                    speaker: "Lukas Eder",
                    company: "Data Geekery GmbH",
                    language: "de",
                    track: "Enterprise Java"),
            new Talk(
                    id : 8,
                    title: "BYOC - Bring Your Own Container",
                    speaker: "Christian Kaltepoth",
                    company: "TU Wien",
                    language: "de",
                    track: "Enterprise Java")
    ]
    def talks = courses.collectEntries {[it.id,it]}

    handlers {
        get("health") {
            ctx -> response.send("yeah")
        }
        handler("talk") {
            byMethod {
                get {
                    render json(talks.values())
                }
                post {
                    def talk = parse (fromJson(Talk))
                    talks.add(talk)
                    render json(talk)
                }

            }
        }
        handler("talk/:id") {
            def query = pathTokens["id"]
            byMethod {
                get {
                    render json (talks.find { talk -> talk.id = query})
                }
                put {
                    def talk = parse (fromJson(Talk))
                    talks.put(query,talk)
                }
                delete {
                    talks.remove(query)
                }
            }
        }
    }
}
