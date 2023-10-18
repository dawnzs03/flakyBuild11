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

class WMNullablePool
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'resourcePlanName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'poolPath',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'allocFraction',
            'isRequired' => false,
            'type' => TType::DOUBLE,
        ),
        4 => array(
            'var' => 'queryParallelism',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        5 => array(
            'var' => 'schedulingPolicy',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'isSetSchedulingPolicy',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        7 => array(
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
    public $poolPath = null;
    /**
     * @var double
     */
    public $allocFraction = null;
    /**
     * @var int
     */
    public $queryParallelism = null;
    /**
     * @var string
     */
    public $schedulingPolicy = null;
    /**
     * @var bool
     */
    public $isSetSchedulingPolicy = null;
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
            if (isset($vals['poolPath'])) {
                $this->poolPath = $vals['poolPath'];
            }
            if (isset($vals['allocFraction'])) {
                $this->allocFraction = $vals['allocFraction'];
            }
            if (isset($vals['queryParallelism'])) {
                $this->queryParallelism = $vals['queryParallelism'];
            }
            if (isset($vals['schedulingPolicy'])) {
                $this->schedulingPolicy = $vals['schedulingPolicy'];
            }
            if (isset($vals['isSetSchedulingPolicy'])) {
                $this->isSetSchedulingPolicy = $vals['isSetSchedulingPolicy'];
            }
            if (isset($vals['ns'])) {
                $this->ns = $vals['ns'];
            }
        }
    }

    public function getName()
    {
        return 'WMNullablePool';
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
                        $xfer += $input->readString($this->poolPath);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::DOUBLE) {
                        $xfer += $input->readDouble($this->allocFraction);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->queryParallelism);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->schedulingPolicy);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->isSetSchedulingPolicy);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
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
        $xfer += $output->writeStructBegin('WMNullablePool');
        if ($this->resourcePlanName !== null) {
            $xfer += $output->writeFieldBegin('resourcePlanName', TType::STRING, 1);
            $xfer += $output->writeString($this->resourcePlanName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->poolPath !== null) {
            $xfer += $output->writeFieldBegin('poolPath', TType::STRING, 2);
            $xfer += $output->writeString($this->poolPath);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->allocFraction !== null) {
            $xfer += $output->writeFieldBegin('allocFraction', TType::DOUBLE, 3);
            $xfer += $output->writeDouble($this->allocFraction);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->queryParallelism !== null) {
            $xfer += $output->writeFieldBegin('queryParallelism', TType::I32, 4);
            $xfer += $output->writeI32($this->queryParallelism);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->schedulingPolicy !== null) {
            $xfer += $output->writeFieldBegin('schedulingPolicy', TType::STRING, 5);
            $xfer += $output->writeString($this->schedulingPolicy);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->isSetSchedulingPolicy !== null) {
            $xfer += $output->writeFieldBegin('isSetSchedulingPolicy', TType::BOOL, 6);
            $xfer += $output->writeBool($this->isSetSchedulingPolicy);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ns !== null) {
            $xfer += $output->writeFieldBegin('ns', TType::STRING, 7);
            $xfer += $output->writeString($this->ns);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
