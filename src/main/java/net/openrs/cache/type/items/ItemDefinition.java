package net.openrs.cache.type.items;

import java.nio.ByteBuffer;
import java.util.Map;

public class ItemDefinition {

    private int ambient;
    private int unnotedId;
    private int notedId;
    private short[] colorToReplace;
    private short[] colorToReplaceWith;
    private int contrast;
    private int price = 1;
    private int[] stackAmounts;
    private int[] stackIds;
    private int femaleHeadModel;
    private int femaleHeadModel2;
    private int femaleModel;
    private int femaleModel1;
    private int femaleModel2;
    private int femaleOffset;
    private final int id;
    private String[] inventoryActions = new String[5];
    private int inventoryModel;
    private int maleHeadModel;
    private int maleHeadModel2;
    private int maleModel = -1;
    private int maleModel1;
    private int maleModel2;
    private int maleOffset;
    private boolean members = false;
    private String name;
    private int note;
    private int notedTemplate;
    private String[] groundActions = new String[5];
    private int resizeX = 128;
    private int resizeY = 128;
    private int resizeZ = 128;
    private int stackable = 0;
    private boolean unnoted;
    private int teamId;
    private short[] textureToReplace;
    private short[] textureToReplaceWith;
    private int modelPitch = 0;
    private int offsetX = 0;
    private int modelRoll = 0;
    private int offsetY = 0;
    private int modelYaw = 0;
    private int zoom = 2000;
    private int placeHolderId;
    private int placeHolderTemplate;
    private int shiftClickIndex = -2;
    private Map<Integer, Object> params = null;

    public ItemDefinition(int id) {
        this.id = id;
    }

    public void decode(ByteBuffer buffer) {

    }

    public int getAmbient() {
        return ambient;
    }

    public void setAmbient(int ambient) {
        this.ambient = ambient;
    }

    public int getUnnotedId() {
        return unnotedId;
    }

    public void setUnnotedId(int unnotedId) {
        this.unnotedId = unnotedId;
    }

    public int getNotedId() {
        return notedId;
    }

    public void setNotedId(int notedId) {
        this.notedId = notedId;
    }

    public short[] getColorToReplace() {
        return colorToReplace;
    }

    public void setColorToReplace(short[] colorToReplace) {
        this.colorToReplace = colorToReplace;
    }

    public short[] getColorToReplaceWith() {
        return colorToReplaceWith;
    }

    public void setColorToReplaceWith(short[] colorToReplaceWith) {
        this.colorToReplaceWith = colorToReplaceWith;
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int[] getStackAmounts() {
        return stackAmounts;
    }

    public void setStackAmounts(int[] stackAmounts) {
        this.stackAmounts = stackAmounts;
    }

    public int[] getStackIds() {
        return stackIds;
    }

    public void setStackIds(int[] stackIds) {
        this.stackIds = stackIds;
    }

    public int getFemaleHeadModel() {
        return femaleHeadModel;
    }

    public void setFemaleHeadModel(int femaleHeadModel) {
        this.femaleHeadModel = femaleHeadModel;
    }

    public int getFemaleHeadModel2() {
        return femaleHeadModel2;
    }

    public void setFemaleHeadModel2(int femaleHeadModel2) {
        this.femaleHeadModel2 = femaleHeadModel2;
    }

    public int getFemaleModel() {
        return femaleModel;
    }

    public void setFemaleModel(int femaleModel) {
        this.femaleModel = femaleModel;
    }

    public int getFemaleModel1() {
        return femaleModel1;
    }

    public void setFemaleModel1(int femaleModel1) {
        this.femaleModel1 = femaleModel1;
    }

    public int getFemaleModel2() {
        return femaleModel2;
    }

    public void setFemaleModel2(int femaleModel2) {
        this.femaleModel2 = femaleModel2;
    }

    public int getFemaleOffset() {
        return femaleOffset;
    }

    public void setFemaleOffset(int femaleOffset) {
        this.femaleOffset = femaleOffset;
    }

    public int getId() {
        return id;
    }

    public String[] getInventoryActions() {
        return inventoryActions;
    }

    public void setInventoryActions(String[] inventoryActions) {
        this.inventoryActions = inventoryActions;
    }

    public int getInventoryModel() {
        return inventoryModel;
    }

    public void setInventoryModel(int inventoryModel) {
        this.inventoryModel = inventoryModel;
    }

    public int getMaleHeadModel() {
        return maleHeadModel;
    }

    public void setMaleHeadModel(int maleHeadModel) {
        this.maleHeadModel = maleHeadModel;
    }

    public int getMaleHeadModel2() {
        return maleHeadModel2;
    }

    public void setMaleHeadModel2(int maleHeadModel2) {
        this.maleHeadModel2 = maleHeadModel2;
    }

    public int getMaleModel() {
        return maleModel;
    }

    public void setMaleModel(int maleModel) {
        this.maleModel = maleModel;
    }

    public int getMaleModel1() {
        return maleModel1;
    }

    public void setMaleModel1(int maleModel1) {
        this.maleModel1 = maleModel1;
    }

    public int getMaleModel2() {
        return maleModel2;
    }

    public void setMaleModel2(int maleModel2) {
        this.maleModel2 = maleModel2;
    }

    public int getMaleOffset() {
        return maleOffset;
    }

    public void setMaleOffset(int maleOffset) {
        this.maleOffset = maleOffset;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getNotedTemplate() {
        return notedTemplate;
    }

    public void setNotedTemplate(int notedTemplate) {
        this.notedTemplate = notedTemplate;
    }

    public String[] getGroundActions() {
        return groundActions;
    }

    public void setGroundActions(String[] groundActions) {
        this.groundActions = groundActions;
    }

    public int getResizeX() {
        return resizeX;
    }

    public void setResizeX(int resizeX) {
        this.resizeX = resizeX;
    }

    public int getResizeY() {
        return resizeY;
    }

    public void setResizeY(int resizeY) {
        this.resizeY = resizeY;
    }

    public int getResizeZ() {
        return resizeZ;
    }

    public void setResizeZ(int resizeZ) {
        this.resizeZ = resizeZ;
    }

    public int getStackable() {
        return stackable;
    }

    public void setStackable(int stackable) {
        this.stackable = stackable;
    }

    public boolean isUnnoted() {
        return unnoted;
    }

    public void setUnnoted(boolean unnoted) {
        this.unnoted = unnoted;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public short[] getTextureToReplace() {
        return textureToReplace;
    }

    public void setTextureToReplace(short[] textureToReplace) {
        this.textureToReplace = textureToReplace;
    }

    public short[] getTextureToReplaceWith() {
        return textureToReplaceWith;
    }

    public void setTextureToReplaceWith(short[] textureToReplaceWith) {
        this.textureToReplaceWith = textureToReplaceWith;
    }

    public int getModelPitch() {
        return modelPitch;
    }

    public void setModelPitch(int modelPitch) {
        this.modelPitch = modelPitch;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getModelRoll() {
        return modelRoll;
    }

    public void setModelRoll(int modelRoll) {
        this.modelRoll = modelRoll;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getModelYaw() {
        return modelYaw;
    }

    public void setModelYaw(int modelYaw) {
        this.modelYaw = modelYaw;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getPlaceHolderId() {
        return placeHolderId;
    }

    public void setPlaceHolderId(int placeHolderId) {
        this.placeHolderId = placeHolderId;
    }

    public int getPlaceHolderTemplate() {
        return placeHolderTemplate;
    }

    public void setPlaceHolderTemplate(int placeHolderTemplate) {
        this.placeHolderTemplate = placeHolderTemplate;
    }

    public int getShiftClickIndex() {
        return shiftClickIndex;
    }

    public void setShiftClickIndex(int shiftClickIndex) {
        this.shiftClickIndex = shiftClickIndex;
    }

    public Map<Integer, Object> getParams() {
        return params;
    }

    public void setParams(Map<Integer, Object> params) {
        this.params = params;
    }

}
