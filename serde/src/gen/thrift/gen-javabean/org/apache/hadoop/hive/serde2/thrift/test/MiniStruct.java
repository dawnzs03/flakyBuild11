/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.serde2.thrift.test;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
public class MiniStruct implements org.apache.thrift.TBase<MiniStruct, MiniStruct._Fields>, java.io.Serializable, Cloneable, Comparable<MiniStruct> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MiniStruct");

  private static final org.apache.thrift.protocol.TField MY_STRING_FIELD_DESC = new org.apache.thrift.protocol.TField("my_string", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MY_ENUM_FIELD_DESC = new org.apache.thrift.protocol.TField("my_enum", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MiniStructStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MiniStructTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String my_string; // optional
  private @org.apache.thrift.annotation.Nullable MyEnum my_enum; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MY_STRING((short)1, "my_string"),
    /**
     * 
     * @see MyEnum
     */
    MY_ENUM((short)2, "my_enum");

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
        case 1: // MY_STRING
          return MY_STRING;
        case 2: // MY_ENUM
          return MY_ENUM;
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
  private static final _Fields optionals[] = {_Fields.MY_STRING,_Fields.MY_ENUM};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MY_STRING, new org.apache.thrift.meta_data.FieldMetaData("my_string", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MY_ENUM, new org.apache.thrift.meta_data.FieldMetaData("my_enum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, MyEnum.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MiniStruct.class, metaDataMap);
  }

  public MiniStruct() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MiniStruct(MiniStruct other) {
    if (other.isSetMy_string()) {
      this.my_string = other.my_string;
    }
    if (other.isSetMy_enum()) {
      this.my_enum = other.my_enum;
    }
  }

  public MiniStruct deepCopy() {
    return new MiniStruct(this);
  }

  @Override
  public void clear() {
    this.my_string = null;
    this.my_enum = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getMy_string() {
    return this.my_string;
  }

  public void setMy_string(@org.apache.thrift.annotation.Nullable java.lang.String my_string) {
    this.my_string = my_string;
  }

  public void unsetMy_string() {
    this.my_string = null;
  }

  /** Returns true if field my_string is set (has been assigned a value) and false otherwise */
  public boolean isSetMy_string() {
    return this.my_string != null;
  }

  public void setMy_stringIsSet(boolean value) {
    if (!value) {
      this.my_string = null;
    }
  }

  /**
   * 
   * @see MyEnum
   */
  @org.apache.thrift.annotation.Nullable
  public MyEnum getMy_enum() {
    return this.my_enum;
  }

  /**
   * 
   * @see MyEnum
   */
  public void setMy_enum(@org.apache.thrift.annotation.Nullable MyEnum my_enum) {
    this.my_enum = my_enum;
  }

  public void unsetMy_enum() {
    this.my_enum = null;
  }

  /** Returns true if field my_enum is set (has been assigned a value) and false otherwise */
  public boolean isSetMy_enum() {
    return this.my_enum != null;
  }

  public void setMy_enumIsSet(boolean value) {
    if (!value) {
      this.my_enum = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case MY_STRING:
      if (value == null) {
        unsetMy_string();
      } else {
        setMy_string((java.lang.String)value);
      }
      break;

    case MY_ENUM:
      if (value == null) {
        unsetMy_enum();
      } else {
        setMy_enum((MyEnum)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MY_STRING:
      return getMy_string();

    case MY_ENUM:
      return getMy_enum();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MY_STRING:
      return isSetMy_string();
    case MY_ENUM:
      return isSetMy_enum();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof MiniStruct)
      return this.equals((MiniStruct)that);
    return false;
  }

  public boolean equals(MiniStruct that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_my_string = true && this.isSetMy_string();
    boolean that_present_my_string = true && that.isSetMy_string();
    if (this_present_my_string || that_present_my_string) {
      if (!(this_present_my_string && that_present_my_string))
        return false;
      if (!this.my_string.equals(that.my_string))
        return false;
    }

    boolean this_present_my_enum = true && this.isSetMy_enum();
    boolean that_present_my_enum = true && that.isSetMy_enum();
    if (this_present_my_enum || that_present_my_enum) {
      if (!(this_present_my_enum && that_present_my_enum))
        return false;
      if (!this.my_enum.equals(that.my_enum))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMy_string()) ? 131071 : 524287);
    if (isSetMy_string())
      hashCode = hashCode * 8191 + my_string.hashCode();

    hashCode = hashCode * 8191 + ((isSetMy_enum()) ? 131071 : 524287);
    if (isSetMy_enum())
      hashCode = hashCode * 8191 + my_enum.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(MiniStruct other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetMy_string(), other.isSetMy_string());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMy_string()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.my_string, other.my_string);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetMy_enum(), other.isSetMy_enum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMy_enum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.my_enum, other.my_enum);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MiniStruct(");
    boolean first = true;

    if (isSetMy_string()) {
      sb.append("my_string:");
      if (this.my_string == null) {
        sb.append("null");
      } else {
        sb.append(this.my_string);
      }
      first = false;
    }
    if (isSetMy_enum()) {
      if (!first) sb.append(", ");
      sb.append("my_enum:");
      if (this.my_enum == null) {
        sb.append("null");
      } else {
        sb.append(this.my_enum);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class MiniStructStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MiniStructStandardScheme getScheme() {
      return new MiniStructStandardScheme();
    }
  }

  private static class MiniStructStandardScheme extends org.apache.thrift.scheme.StandardScheme<MiniStruct> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MiniStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MY_STRING
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.my_string = iprot.readString();
              struct.setMy_stringIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MY_ENUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.my_enum = org.apache.hadoop.hive.serde2.thrift.test.MyEnum.findByValue(iprot.readI32());
              struct.setMy_enumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MiniStruct struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.my_string != null) {
        if (struct.isSetMy_string()) {
          oprot.writeFieldBegin(MY_STRING_FIELD_DESC);
          oprot.writeString(struct.my_string);
          oprot.writeFieldEnd();
        }
      }
      if (struct.my_enum != null) {
        if (struct.isSetMy_enum()) {
          oprot.writeFieldBegin(MY_ENUM_FIELD_DESC);
          oprot.writeI32(struct.my_enum.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MiniStructTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MiniStructTupleScheme getScheme() {
      return new MiniStructTupleScheme();
    }
  }

  private static class MiniStructTupleScheme extends org.apache.thrift.scheme.TupleScheme<MiniStruct> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MiniStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMy_string()) {
        optionals.set(0);
      }
      if (struct.isSetMy_enum()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetMy_string()) {
        oprot.writeString(struct.my_string);
      }
      if (struct.isSetMy_enum()) {
        oprot.writeI32(struct.my_enum.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MiniStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.my_string = iprot.readString();
        struct.setMy_stringIsSet(true);
      }
      if (incoming.get(1)) {
        struct.my_enum = org.apache.hadoop.hive.serde2.thrift.test.MyEnum.findByValue(iprot.readI32());
        struct.setMy_enumIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

