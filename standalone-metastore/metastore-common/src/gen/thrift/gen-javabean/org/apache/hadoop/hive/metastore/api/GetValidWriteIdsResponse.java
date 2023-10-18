/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class GetValidWriteIdsResponse implements org.apache.thrift.TBase<GetValidWriteIdsResponse, GetValidWriteIdsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetValidWriteIdsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetValidWriteIdsResponse");

  private static final org.apache.thrift.protocol.TField TBL_VALID_WRITE_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("tblValidWriteIds", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetValidWriteIdsResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetValidWriteIdsResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<TableValidWriteIds> tblValidWriteIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TBL_VALID_WRITE_IDS((short)1, "tblValidWriteIds");

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
        case 1: // TBL_VALID_WRITE_IDS
          return TBL_VALID_WRITE_IDS;
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
    tmpMap.put(_Fields.TBL_VALID_WRITE_IDS, new org.apache.thrift.meta_data.FieldMetaData("tblValidWriteIds", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TableValidWriteIds.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetValidWriteIdsResponse.class, metaDataMap);
  }

  public GetValidWriteIdsResponse() {
  }

  public GetValidWriteIdsResponse(
    java.util.List<TableValidWriteIds> tblValidWriteIds)
  {
    this();
    this.tblValidWriteIds = tblValidWriteIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetValidWriteIdsResponse(GetValidWriteIdsResponse other) {
    if (other.isSetTblValidWriteIds()) {
      java.util.List<TableValidWriteIds> __this__tblValidWriteIds = new java.util.ArrayList<TableValidWriteIds>(other.tblValidWriteIds.size());
      for (TableValidWriteIds other_element : other.tblValidWriteIds) {
        __this__tblValidWriteIds.add(new TableValidWriteIds(other_element));
      }
      this.tblValidWriteIds = __this__tblValidWriteIds;
    }
  }

  public GetValidWriteIdsResponse deepCopy() {
    return new GetValidWriteIdsResponse(this);
  }

  @Override
  public void clear() {
    this.tblValidWriteIds = null;
  }

  public int getTblValidWriteIdsSize() {
    return (this.tblValidWriteIds == null) ? 0 : this.tblValidWriteIds.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<TableValidWriteIds> getTblValidWriteIdsIterator() {
    return (this.tblValidWriteIds == null) ? null : this.tblValidWriteIds.iterator();
  }

  public void addToTblValidWriteIds(TableValidWriteIds elem) {
    if (this.tblValidWriteIds == null) {
      this.tblValidWriteIds = new java.util.ArrayList<TableValidWriteIds>();
    }
    this.tblValidWriteIds.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<TableValidWriteIds> getTblValidWriteIds() {
    return this.tblValidWriteIds;
  }

  public void setTblValidWriteIds(@org.apache.thrift.annotation.Nullable java.util.List<TableValidWriteIds> tblValidWriteIds) {
    this.tblValidWriteIds = tblValidWriteIds;
  }

  public void unsetTblValidWriteIds() {
    this.tblValidWriteIds = null;
  }

  /** Returns true if field tblValidWriteIds is set (has been assigned a value) and false otherwise */
  public boolean isSetTblValidWriteIds() {
    return this.tblValidWriteIds != null;
  }

  public void setTblValidWriteIdsIsSet(boolean value) {
    if (!value) {
      this.tblValidWriteIds = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TBL_VALID_WRITE_IDS:
      if (value == null) {
        unsetTblValidWriteIds();
      } else {
        setTblValidWriteIds((java.util.List<TableValidWriteIds>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TBL_VALID_WRITE_IDS:
      return getTblValidWriteIds();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TBL_VALID_WRITE_IDS:
      return isSetTblValidWriteIds();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof GetValidWriteIdsResponse)
      return this.equals((GetValidWriteIdsResponse)that);
    return false;
  }

  public boolean equals(GetValidWriteIdsResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_tblValidWriteIds = true && this.isSetTblValidWriteIds();
    boolean that_present_tblValidWriteIds = true && that.isSetTblValidWriteIds();
    if (this_present_tblValidWriteIds || that_present_tblValidWriteIds) {
      if (!(this_present_tblValidWriteIds && that_present_tblValidWriteIds))
        return false;
      if (!this.tblValidWriteIds.equals(that.tblValidWriteIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTblValidWriteIds()) ? 131071 : 524287);
    if (isSetTblValidWriteIds())
      hashCode = hashCode * 8191 + tblValidWriteIds.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetValidWriteIdsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetTblValidWriteIds(), other.isSetTblValidWriteIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTblValidWriteIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tblValidWriteIds, other.tblValidWriteIds);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetValidWriteIdsResponse(");
    boolean first = true;

    sb.append("tblValidWriteIds:");
    if (this.tblValidWriteIds == null) {
      sb.append("null");
    } else {
      sb.append(this.tblValidWriteIds);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetTblValidWriteIds()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'tblValidWriteIds' is unset! Struct:" + toString());
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

  private static class GetValidWriteIdsResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetValidWriteIdsResponseStandardScheme getScheme() {
      return new GetValidWriteIdsResponseStandardScheme();
    }
  }

  private static class GetValidWriteIdsResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetValidWriteIdsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetValidWriteIdsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TBL_VALID_WRITE_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list828 = iprot.readListBegin();
                struct.tblValidWriteIds = new java.util.ArrayList<TableValidWriteIds>(_list828.size);
                @org.apache.thrift.annotation.Nullable TableValidWriteIds _elem829;
                for (int _i830 = 0; _i830 < _list828.size; ++_i830)
                {
                  _elem829 = new TableValidWriteIds();
                  _elem829.read(iprot);
                  struct.tblValidWriteIds.add(_elem829);
                }
                iprot.readListEnd();
              }
              struct.setTblValidWriteIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetValidWriteIdsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tblValidWriteIds != null) {
        oprot.writeFieldBegin(TBL_VALID_WRITE_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.tblValidWriteIds.size()));
          for (TableValidWriteIds _iter831 : struct.tblValidWriteIds)
          {
            _iter831.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetValidWriteIdsResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetValidWriteIdsResponseTupleScheme getScheme() {
      return new GetValidWriteIdsResponseTupleScheme();
    }
  }

  private static class GetValidWriteIdsResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetValidWriteIdsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetValidWriteIdsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.tblValidWriteIds.size());
        for (TableValidWriteIds _iter832 : struct.tblValidWriteIds)
        {
          _iter832.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetValidWriteIdsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list833 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRUCT);
        struct.tblValidWriteIds = new java.util.ArrayList<TableValidWriteIds>(_list833.size);
        @org.apache.thrift.annotation.Nullable TableValidWriteIds _elem834;
        for (int _i835 = 0; _i835 < _list833.size; ++_i835)
        {
          _elem834 = new TableValidWriteIds();
          _elem834.read(iprot);
          struct.tblValidWriteIds.add(_elem834);
        }
      }
      struct.setTblValidWriteIdsIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

