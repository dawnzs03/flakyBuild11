<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
use Thrift\Base\TBase;
use Thrift\Type\TType;
use Thrift\Type\TMessageType;
use Thrift\Exception\TException;
use Thrift\Exception\TProtocolException;
use Thrift\Protocol\TProtocol;
use Thrift\Protocol\TBinaryProtocolAccelerated;
use Thrift\Exception\TApplicationException;

class WMGetResourcePlanRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'resourcePlanName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'ns',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var string
     */
    public $resourcePlanName = null;
    /**
     * @var string
     */
    public $ns = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['resourcePlanName'])) {
                $this->resourcePlanName = $vals['resourcePlanName'];
            }
            if (isset($vals['ns'])) {
                $this->ns = $vals['ns'];
            }
        }
    }

    public function getName()
    {
        return 'WMGetResourcePlanRequest';
    }


    public function read($input)
    {
        $xfer = 0;
        $fname = null;
        $ftype = 0;
        $fid = 0;
        $xfer += $input->readStructBegin($fname);
        while (true) {
            $xfer += $input->readFieldBegin($fname, $ftype, $fid);
            if ($ftype == TType::STOP) {
                break;
            }
            switch ($fid) {
                case 1:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->resourcePlanName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->ns);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                default:
                    $xfer += $input->skip($ftype);
                    break;
            }
            $xfer += $input->readFieldEnd();
        }
        $xfer += $input->readStructEnd();
        return $xfer;
    }

    public function write($output)
    {
        $xfer = 0;
        $xfer += $output->writeStructBegin('WMGetResourcePlanRequest');
        if ($this->resourcePlanName !== null) {
            $xfer += $output->writeFieldBegin('resourcePlanName', TType::STRING, 1);
            $xfer += $output->writeString($this->resourcePlanName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ns !== null) {
            $xfer += $output->writeFieldBegin('ns', TType::STRING, 2);
            $xfer += $output->writeString($this->ns);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
