/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class ObjectDictionary implements org.apache.thrift.TBase<ObjectDictionary, ObjectDictionary._Fields>, java.io.Serializable, Cloneable, Comparable<ObjectDictionary> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ObjectDictionary");

  private static final org.apache.thrift.protocol.TField VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("values", org.apache.thrift.protocol.TType.MAP, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ObjectDictionaryStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ObjectDictionaryTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>> values; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VALUES((short)1, "values");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // VALUES
          return VALUES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VALUES, new org.apache.thrift.meta_data.FieldMetaData("values", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING                , true)))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ObjectDictionary.class, metaDataMap);
  }

  public ObjectDictionary() {
  }

  public ObjectDictionary(
    java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>> values)
  {
    this();
    this.values = values;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ObjectDictionary(ObjectDictionary other) {
    if (other.isSetValues()) {
      java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>> __this__values = new java.util.HashMap<java.lang.String,java.util.List<java.nio.ByteBuffer>>(other.values.size());
      for (java.util.Map.Entry<java.lang.String, java.util.List<java.nio.ByteBuffer>> other_element : other.values.entrySet()) {

        java.lang.String other_element_key = other_element.getKey();
        java.util.List<java.nio.ByteBuffer> other_element_value = other_element.getValue();

        java.lang.String __this__values_copy_key = other_element_key;

        java.util.List<java.nio.ByteBuffer> __this__values_copy_value = new java.util.ArrayList<java.nio.ByteBuffer>(other_element_value);

        __this__values.put(__this__values_copy_key, __this__values_copy_value);
      }
      this.values = __this__values;
    }
  }

  public ObjectDictionary deepCopy() {
    return new ObjectDictionary(this);
  }

  @Override
  public void clear() {
    this.values = null;
  }

  public int getValuesSize() {
    return (this.values == null) ? 0 : this.values.size();
  }

  public void putToValues(java.lang.String key, java.util.List<java.nio.ByteBuffer> val) {
    if (this.values == null) {
      this.values = new java.util.HashMap<java.lang.String,java.util.List<java.nio.ByteBuffer>>();
    }
    this.values.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>> getValues() {
    return this.values;
  }

  public void setValues(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>> values) {
    this.values = values;
  }

  public void unsetValues() {
    this.values = null;
  }

  /** Returns true if field values is set (has been assigned a value) and false otherwise */
  public boolean isSetValues() {
    return this.values != null;
  }

  public void setValuesIsSet(boolean value) {
    if (!value) {
      this.values = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case VALUES:
      if (value == null) {
        unsetValues();
      } else {
        setValues((java.util.Map<java.lang.String,java.util.List<java.nio.ByteBuffer>>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case VALUES:
      return getValues();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case VALUES:
      return isSetValues();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof ObjectDictionary)
      return this.equals((ObjectDictionary)that);
    return false;
  }

  public boolean equals(ObjectDictionary that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_values = true && this.isSetValues();
    boolean that_present_values = true && that.isSetValues();
    if (this_present_values || that_present_values) {
      if (!(this_present_values && that_present_values))
        return false;
      if (!this.values.equals(that.values))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetValues()) ? 131071 : 524287);
    if (isSetValues())
      hashCode = hashCode * 8191 + values.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ObjectDictionary other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetValues(), other.isSetValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.values, other.values);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ObjectDictionary(");
    boolean first = true;

    sb.append("values:");
    if (this.values == null) {
      sb.append("null");
    } else {
      sb.append(this.values);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetValues()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'values' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ObjectDictionaryStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ObjectDictionaryStandardScheme getScheme() {
      return new ObjectDictionaryStandardScheme();
    }
  }

  private static class ObjectDictionaryStandardScheme extends org.apache.thrift.scheme.StandardScheme<ObjectDictionary> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ObjectDictionary struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map334 = iprot.readMapBegin();
                struct.values = new java.util.HashMap<java.lang.String,java.util.List<java.nio.ByteBuffer>>(2*_map334.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key335;
                @org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> _val336;
                for (int _i337 = 0; _i337 < _map334.size; ++_i337)
                {
                  _key335 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TList _list338 = iprot.readListBegin();
                    _val336 = new java.util.ArrayList<java.nio.ByteBuffer>(_list338.size);
                    @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem339;
                    for (int _i340 = 0; _i340 < _list338.size; ++_i340)
                    {
                      _elem339 = iprot.readBinary();
                      _val336.add(_elem339);
                    }
                    iprot.readListEnd();
                  }
                  struct.values.put(_key335, _val336);
                }
                iprot.readMapEnd();
              }
              struct.setValuesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ObjectDictionary struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.values != null) {
        oprot.writeFieldBegin(VALUES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.LIST, struct.values.size()));
          for (java.util.Map.Entry<java.lang.String, java.util.List<java.nio.ByteBuffer>> _iter341 : struct.values.entrySet())
          {
            oprot.writeString(_iter341.getKey());
            {
              oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, _iter341.getValue().size()));
              for (java.nio.ByteBuffer _iter342 : _iter341.getValue())
              {
                oprot.writeBinary(_iter342);
              }
              oprot.writeListEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ObjectDictionaryTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ObjectDictionaryTupleScheme getScheme() {
      return new ObjectDictionaryTupleScheme();
    }
  }

  private static class ObjectDictionaryTupleScheme extends org.apache.thrift.scheme.TupleScheme<ObjectDictionary> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ObjectDictionary struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.values.size());
        for (java.util.Map.Entry<java.lang.String, java.util.List<java.nio.ByteBuffer>> _iter343 : struct.values.entrySet())
        {
          oprot.writeString(_iter343.getKey());
          {
            oprot.writeI32(_iter343.getValue().size());
            for (java.nio.ByteBuffer _iter344 : _iter343.getValue())
            {
              oprot.writeBinary(_iter344);
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ObjectDictionary struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TMap _map345 = iprot.readMapBegin(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.LIST); 
        struct.values = new java.util.HashMap<java.lang.String,java.util.List<java.nio.ByteBuffer>>(2*_map345.size);
        @org.apache.thrift.annotation.Nullable java.lang.String _key346;
        @org.apache.thrift.annotation.Nullable java.util.List<java.nio.ByteBuffer> _val347;
        for (int _i348 = 0; _i348 < _map345.size; ++_i348)
        {
          _key346 = iprot.readString();
          {
            org.apache.thrift.protocol.TList _list349 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
            _val347 = new java.util.ArrayList<java.nio.ByteBuffer>(_list349.size);
            @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _elem350;
            for (int _i351 = 0; _i351 < _list349.size; ++_i351)
            {
              _elem350 = iprot.readBinary();
              _val347.add(_elem350);
            }
          }
          struct.values.put(_key346, _val347);
        }
      }
      struct.setValuesIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

