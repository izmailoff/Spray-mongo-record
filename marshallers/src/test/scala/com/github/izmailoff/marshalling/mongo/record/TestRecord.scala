package com.github.izmailoff.marshalling.mongo.record

import net.liftweb.mongodb.record.{BsonMetaRecord, BsonRecord}
import net.liftweb.record.field.StringField

class TestRecord extends BsonRecord[TestRecord] {
  def meta = TestRecord

  object testName extends StringField(this, 30)

}

object TestRecord extends TestRecord with BsonMetaRecord[TestRecord]