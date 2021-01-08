/**
* ChangeOwner function
* @param {org.assay.ProduceDevice} newDevice - The transaction that produce the device.
* @transaction
*
*/
async function produceDevice(newDevice){
    
    const participantRegistry = await getParticipantRegistry('org.assay.Manufacturer');
    var NS = 'org.assay';
    var device = getFactory().newResource(NS,'Device',newDevice.deviceId)
    device.testName = newDevice.testName
    device.manufacturer = newDevice.manufacturer
    device.dateOfManufacture = newDevice.dateOfManufacture
    device.expireDate = newDevice.expireDate
    device.benchNumber = newDevice.benchNumber
    device.productionPlace  = newDevice.productionPlace 
    device.status= newDevice.status


    const assetRegistry = await getAssetRegistry('org.assay.Device');
    await assetRegistry.add(device);
    await participantRegistry.update(newDevice.manufacturer);
  
  	const ProduceDeviceEvent = getFactory().newEvent('org.assay','ProduceDeviceEvent')
    ProduceDeviceEvent.deviceId = device.deviceId;
    ProduceDeviceEvent.devicestatus = device.status;
   	emit(ProduceDeviceEvent);
  
  
}

/**
*@param {org.assay.DoTheTest} newDevice- the DoTheTest transaction
*@transaction
*/
async function doTheTest(newDevice) {
   
    const participantRegistry = await getParticipantRegistry('org.assay.Operator');
  	const assetRegistry = await getAssetRegistry('org.assay.Device');
  	const device = await assetRegistry.get(newDevice.deviceId);

   // var NS = 'org.assay';
    //var device = getFactory().newResource(NS,'Device',Device.deviceId)
	
	device.status = newDevice.status 
 	device.operator = newDevice.operator 
  	device.testDate = newDevice.testDate 
 	device.patientId = newDevice.patientId 
  	device.gender = newDevice.gender
  	device.weight = newDevice.weight
 	device.url=newDevice.url 
  	device.result = newDevice.result
  	device.testPlace = newDevice.result 

    await assetRegistry.update(device);
    await participantRegistry.update(newDevice.operator);
   
  	const DoTheTestEvent  = getFactory().newEvent('org.assay','DoTheTestEvent');
  	DoTheTestEvent.deviceId = device.deviceId;
    DoTheTestEvent.devicestatus = device.status;
   	emit(DoTheTestEvent);
   
}