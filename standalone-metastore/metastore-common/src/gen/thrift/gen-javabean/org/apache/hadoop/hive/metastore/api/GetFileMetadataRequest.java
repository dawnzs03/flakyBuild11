/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class GetFileMetadataRequest implements org.apache.thrift.TBase<GetFileMetadataRequest, GetFileMetadataRequest._Fields>, java.io.Serializable, Cloneable, Comparable<GetFileMetadataRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetFileMetadataRequest");

  private static final org.apache.thrift.protocol.TField FILE_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("fileIds", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetFileMetadataRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetFileMetadataRequestTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> fileIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_IDS((short)1, "fileIds");

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
        case 1: // FILE_IDS
          return FILE_IDS;
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
    tmpMap.put(_Fields.FILE_IDS, new org.apache.thrift.meta_data.FieldMetaData("fileIds", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetFileMetadataRequest.class, metaDataMap);
  }

  public GetFileMetadataRequest() {
  }

  public GetFileMetadataRequest(
    java.util.List<java.lang.Long> fileIds)
  {
    this();
    this.fileIds = fileIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetFileMetadataRequest(GetFileMetadataRequest other) {
    if (other.isSetFileIds()) {
      java.util.List<java.lang.Long> __this__fileIds = new java.util.ArrayList<java.lang.Long>(other.fileIds);
      this.fileIds = __this__fileIds;
    }
  }

  public GetFileMetadataRequest deepCopy() {
    return new GetFileMetadataRequest(this);
  }

  @Override
  public void clear() {
    this.fileIds = null;
  }

  public int getFileIdsSize() {
    return (this.fileIds == null) ? 0 : this.fileIds.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.Long> getFileIdsIterator() {
    return (this.fileIds == null) ? null : this.fileIds.iterator();
  }

  public void addToFileIds(long elem) {
    if (this.fileIds == null) {
      this.fileIds = new java.util.ArrayList<java.lang.Long>();
    }
    this.fileIds.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.Long> getFileIds() {
    return this.fileIds;
  }

  public void setFileIds(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> fileIds) {
    this.fileIds = fileIds;
  }

  public void unsetFileIds() {
    this.fileIds = null;
  }

  /** Returns true if field fileIds is set (has been assigned a value) and false otherwise */
  public boolean isSetFileIds() {
    return this.fileIds != null;
  }

  public void setFileIdsIsSet(boolean value) {
    if (!value) {
      this.fileIds = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FILE_IDS:
      if (value == null) {
        unsetFileIds();
      } else {
        setFileIds((java.util.List<java.lang.Long>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_IDS:
      return getFileIds();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE_IDS:
      return isSetFileIds();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof GetFileMetadataRequest)
      return this.equals((GetFileMetadataRequest)that);
    return false;
  }

  public boolean equals(GetFileMetadataRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fileIds = true && this.isSetFileIds();
    boolean that_present_fileIds = true && that.isSetFileIds();
    if (this_present_fileIds || that_present_fileIds) {
      if (!(this_present_fileIds && that_present_fileIds))
        return false;
      if (!this.fileIds.equals(that.fileIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFileIds()) ? 131071 : 524287);
    if (isSetFileIds())
      hashCode = hashCode * 8191 + fileIds.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetFileMetadataRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetFileIds(), other.isSetFileIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileIds, other.fileIds);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetFileMetadataRequest(");
    boolean first = true;

    sb.append("fileIds:");
    if (this.fileIds == null) {
      sb.append("null");
    } else {
      sb.append(this.fileIds);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetFileIds()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'fileIds' is unset! Struct:" + toString());
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

  private static class GetFileMetadataRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetFileMetadataRequestStandardScheme getScheme() {
      return new GetFileMetadataRequestStandardScheme();
    }
  }

  private static class GetFileMetadataRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetFileMetadataRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetFileMetadataRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list1078 = iprot.readListBegin();
                struct.fileIds = new java.util.ArrayList<java.lang.Long>(_list1078.size);
                long _elem1079;
                for (int _i1080 = 0; _i1080 < _list1078.size; ++_i1080)
                {
                  _elem1079 = iprot.readI64();
                  struct.fileIds.add(_elem1079);
                }
                iprot.readListEnd();
              }
              struct.setFileIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetFileMetadataRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fileIds != null) {
        oprot.writeFieldBegin(FILE_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.fileIds.size()));
          for (long _iter1081 : struct.fileIds)
          {
            oprot.writeI64(_iter1081);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetFileMetadataRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetFileMetadataRequestTupleScheme getScheme() {
      return new GetFileMetadataRequestTupleScheme();
    }
  }

  private static class GetFileMetadataRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetFileMetadataRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetFileMetadataRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.fileIds.size());
        for (long _iter1082 : struct.fileIds)
        {
          oprot.writeI64(_iter1082);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetFileMetadataRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list1083 = iprot.readListBegin(org.apache.thrift.protocol.TType.I64);
        struct.fileIds = new java.util.ArrayList<java.lang.Long>(_list1083.size);
        long _elem1084;
        for (int _i1085 = 0; _i1085 < _list1083.size; ++_i1085)
        {
          _elem1084 = iprot.readI64();
          struct.fileIds.add(_elem1084);
        }
      }
      struct.setFileIdsIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

