package com.sandbox
package runner

import org.scalatest._
import tagobjects.Retryable

import com.sandbox.models.{ LocalStorage, Context }

class RunTasks extends FlatSpec with BeforeAndAfterEach with Retries {
  implicit var context = Context(Context.getOrCreateSession())

  override def beforeEach() = {
    context = Context(Context.getOrCreateSession())
  }

  override def afterEach() = {
    context.session.close()
  }

  override def withFixture(test: NoArgTest) = {
    if (isRetryable(test))
      withRetry { super.withFixture(test) }
    else
      super.withFixture(test)
  }

  import wikipedia.tasks.{ FetchWikipediaTask, ShowAnalysisTask }
  val storage = LocalStorage("/tmp/wiki-data")

  "League standings" should "fetch Wikipedia pages" taggedAs (Retryable) in {
    FetchWikipediaTask(output = storage).run()
    // Make checks here
  }

  it should "show summary analysis" in {
    ShowAnalysisTask(input = storage).run()
    // Make checks here
  }

}
