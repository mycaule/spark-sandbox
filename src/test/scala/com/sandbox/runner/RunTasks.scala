package com.sandbox
package runner

import org.scalatest._
import com.sandbox.models.{ LocalStorage, Context }

class RunTasks extends FunSpec with BeforeAndAfterEach {
  implicit var context = Context(Context.getOrCreateSession())

  override protected def beforeEach() = {
    context = Context(Context.getOrCreateSession())
  }

  override protected def afterEach() = {
    context.session.close()
  }

  describe("League standings from Wikipedia") {
    import wikipedia.tasks.{ FetchWikipediaTask, ShowAnalysisTask }
    val storage = LocalStorage("/tmp/wiki-data")

    it("fetches Wikipedia pages") {
      FetchWikipediaTask(output = storage).run()
      // Make checks here
    }

    it("shows summary analysis") {
      ShowAnalysisTask(input = storage).run()
      // Make checks here
    }
  }

}
