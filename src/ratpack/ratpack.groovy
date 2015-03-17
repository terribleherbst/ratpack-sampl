import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

class Course {
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

    talks = [
            new Course(title: "Lambdas and Streams, Functional Programming in JDK8",
                    speaker: "Simon Ritter",
                    company: "Oracle",
                    language: "en",
                    track: "Core Java"),
            new Course(title: "Understanding Java byte code",
                    speaker: "Rafael Winterhalter",
                    company: "Kantega AS",
                    language: "en",
                    track: "Core Java"),
            new Course(title: "Legacy",
                    speaker: "Peter Lawrey",
                    company: "Higher Frequency Trading Ltd.",
                    language: "en",
                    track: "Core Java"),
            new Course(title: "Lessons",
                    speaker: "Aron Gupta",
                    company: "Red Hat Inc",
                    language: "en",
                    track: "Enterprise Java"),
            new Course(title: "JBatch mit Apache BatchEE",
                    speaker: "Mark Strutberg",
                    company: "TU Wien",
                    language: "de",
                    track: "Enterprise Java"),
            new Course(title: "Where are we with MVC in JavaEE 8",
                    speaker: "Manfred Riem",
                    company: "Oracle",
                    language: "en",
                    track: "Enterprise Java"),
            new Course(title: "2000 Zeilen Java Code oder 50 Zeilen SQL",
                    speaker: "Lukas Eder",
                    company: "Data Geekery GmbH",
                    language: "de",
                    track: "Enterprise Java"),
            new Course(title: "BYOC - Bring Your Own Container",
                    speaker: "Christian Kaltepoth",
                    company: "TU Wien",
                    language: "de",
                    track: "Enterprise Java")
    ]
    handlers {
        get("health") {
            ctx -> response.send("yeah")
        }
        handler("talk") {
            render json(talks)
        }
        handler("talk/:title") {
            def query = pathTokens["title"]
            render json (talks.find { talk -> talk.title.contains(query)})
        }
    }
}
