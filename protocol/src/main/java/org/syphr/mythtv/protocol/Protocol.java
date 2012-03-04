/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.channels.ByteChannel;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.FileEntry;
import org.syphr.mythtv.data.FileInfo;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;
import org.syphr.mythtv.data.PixMap;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.RecorderDevice;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.data.RecordingsInProgress;
import org.syphr.mythtv.data.TimeInfo;
import org.syphr.mythtv.data.UpcomingRecordings;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.Verbose;

/**
 * This interface represents the combined API of all MythTV protocols that are
 * supported. However, any functionality that is not part of the protocol
 * present in the most current stable release of MythTV will be marked as
 * deprecated.
 * 
 * @author Gregory P. Moyer
 */
public interface Protocol
{
    /**
     * Open a socket connection to the given host. This method will block until
     * the connection completes. If a connection is already active, this method
     * will do nothing.<br>
     * <br>
     * Note that this method does not initiate any protocol commands - it merely
     * creates the low-level connection.
     * 
     * @param host
     *            the hostname (or IP address) of the server
     * @param port
     *            the port on the server
     * @param timeout
     *            number of milliseconds to wait before assuming the connection
     *            failed (values < 1 indicate no timeout)
     * @throws IOException
     *             if the connection could not be completed
     */
    public void connect(String host, int port, final long timeout) throws IOException;

    /**
     * Determine whether or not there is an active connection.
     * 
     * @return <code>true</code> if there is an active connection;
     *         <code>false</code> otherwise
     */
    public boolean isConnected();

    /**
     * Identify this protocol to the backend and verify that the backend
     * understands the same protocol. This should be the first command sent once
     * a connection is established.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws InvalidProtocolVersionException
     *             if the backend does not speak the same protocol
     * 
     * @since 63
     */
    public void mythProtoVersion() throws IOException, InvalidProtocolVersionException;

    /**
     * Announce this connection to the backend. This should be the first command
     * sent to the backend after {@link #mythProtoVersion()}.<br>
     * <br>
     * Note: if a file is to be transferred,
     * {@link #annFileTransfer(String, FileTransferType, boolean, long, URI, String, Protocol)}
     * should be sent first.
     * 
     * @param connectionType
     *            the intended use for this connection
     * @param host
     *            the name of the host that is being announced (the client)
     * @param level
     *            the level of events desired by the client
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void ann(ConnectionType connectionType, String host, EventLevel level) throws IOException;

    /**
     * Announce this connection to the backend as a slave backend. This should
     * be the first command sent to the backend after
     * {@link #mythProtoVersion()}.
     * 
     * @param address
     *            the address of this host
     * @param recordings
     *            all recordings that are managed by this slave backend
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void annSlaveBackend(InetAddress address, Program... recordings) throws IOException;

    /**
     * Announce this connection to the backend as a media server. This should be
     * the first command sent to the backend after {@link #mythProtoVersion()}.
     * 
     * @param address
     *            the address of this host
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 67
     */
    public void annMediaServer(InetAddress address) throws IOException;

    /**
     * Announce this connection to the backend with the intention of
     * transferring a file (read or write). This should be the first command
     * sent to the backend after {@link #mythProtoVersion()}.<br>
     * <br>
     * If a file transfer is not needed, use
     * {@link #ann(ConnectionType, String, EventLevel)} instead.
     * 
     * @param host
     *            the name of the host that is being announced (the client)
     * @param type
     *            how the transfer connection will be used (read/write)
     * @param readAhead
     *            if <code>true</code>, the backend will spawn a read ahead
     *            thread
     * @param timeout
     *            milliseconds to timeout the transfer
     * @param uri
     *            the URI of the file to transfer, relative to the storage group
     * @param storageGroup
     *            the storage group that contains (will contain) the file to be
     *            transferred (optional, can be <code>null</code>, in which case
     *            any and all storage groups are used)
     * @param commandProtocol
     *            the protocol that will be used to send file transfer commands
     * @return a sub-protocol API that can be used to manipulate the data stream
     * @throws IOException
     *             if there is a communication or protocol error
     */
    public QueryFileTransfer annFileTransfer(String host,
                                             FileTransferType type,
                                             boolean readAhead,
                                             long timeout,
                                             URI uri,
                                             String storageGroup,
                                             Protocol commandProtocol) throws IOException;

    /**
     * Tell the backend that this client intends to disconnect. This command
     * also guarantees that the socket connection to the host will be closed
     * when it completes.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void done() throws IOException;

    /**
     * Retrieve a new protocol instance with a new connection using the same
     * parameters as this instance.
     * 
     * @return the new protocol
     * @throws IOException
     *             if there is an error while creating the new connection
     */
    public Protocol newProtocol() throws IOException;

    /**
     * Redirect this I/O channel for this protocol. Until
     * {@link ByteChannel#close()} is called, no methods in this protocol
     * instance can be used. This is useful for handling file transfers.
     * 
     * @return the underlying I/O channel
     */
    public ByteChannel getChannel();

    /**
     * Retrieve the low-level communications manager. In most cases, it should
     * not be necessary to access this object directly.
     * 
     * @return the socket manager that controls communication with the backend
     */
    public SocketManager getSocketManager();

    /**
     * Set the handler used for unsupported operations.
     * 
     * @param handler
     *            the handler to use
     */
    public void setUnsupportedHandler(UnsupportedHandler handler);

    /**
     * Allow the backend to shutdown. This releases a previous a call to
     * {@link #blockShutdown()}.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     */
    public void allowShutdown() throws IOException;

    /**
     * Prevent this backend from shutting down until it is releases via
     * {@link #allowShutdown()}.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     */
    public void blockShutdown() throws IOException;

    /**
     * Determine whether or not the given program is currently being recorded
     * and on which recorder.
     * 
     * @param program
     *            the program to check
     * @return the ID of the recorder that is actively recording the program or
     *         <code>0</code> if the program is not currently being recorded
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public int checkRecording(Program program) throws IOException;

    /**
     * Request that a file be deleted.
     * 
     * @param filename
     *            the file to delete, relative to the storage group
     * @param storageGroup
     *            the storage group where the file exists (optional, can be
     *            <code>null</code>, in which case any and all storage groups
     *            are used)
     * @return <code>true</code> if the delete was successful;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public boolean deleteFile(URI filename, String storageGroup) throws IOException;

    /**
     * Request that a recording be deleted. To only remove a recording from the
     * history, see {@link #forgetRecording(Program)}.
     * 
     * @param channel
     *            the channel on which the program was recorded
     * @param recStartTs
     *            the actual (not scheduled) start time of the recording
     * @param force
     *            if <code>true</code>, metadata will be removed even if the
     *            file cannot be located
     * @param forget
     *            if <code>true</code>, the history of this recording will be
     *            removed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recording cannot be located
     * 
     * @since 63
     */
    public void deleteRecording(Channel channel, Date recStartTs, boolean force, boolean forget) throws IOException,
                                                                                                CommandException;

    /**
     * Request that the backend manage a file download. This command will return
     * immediately. To watch the progress, listen for backend events.
     * 
     * @see #downloadFileNow(URL, String, URI)
     * 
     * @param url
     *            the URL of the item to download
     * @param storageGroup
     *            the destination storage group (optional, can be
     *            <code>null</code>, in which case any and all storage groups
     *            are used)
     * @param filename
     *            the location to save, relative to the storage group
     * @return the URI of the new file or <code>null</code> if an error occurred
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if an error occurs while attempting the download
     * 
     * @since 63
     */
    public URI downloadFile(URL url, String storageGroup, URI filename) throws IOException,
                                                                       CommandException;

    /**
     * Request that the backend manage a file download. This command will not
     * return until the download completes.
     * 
     * @see #downloadFile(URL, String, URI)
     * 
     * @param url
     *            the URL of the item to download
     * @param storageGroup
     *            the destination storage group (optional, can be
     *            <code>null</code>, in which case any and all storage groups
     *            are used)
     * @param filename
     *            the location to save, relative to the storage group
     * @return the URI of the new file or <code>null</code> if an error occurred
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if an error occurs while attempting the download
     * 
     * @since 63
     */
    public URI downloadFileNow(URL url, String storageGroup, URI filename) throws IOException,
                                                                          CommandException;

    /**
     * Change the path to a URI if necessary and fill in the file size for the
     * given program. If there is no path set in the given program, this request
     * will do nothing.
     * 
     * @param host
     *            used to determine if the returned path should be local or
     *            remote
     * @param program
     *            the basic program information
     * @return the given program with path and file size filled in for the given
     *         host
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public Program fillProgramInfo(String host, Program program) throws IOException;

    /**
     * Mark a recording so that it will never be considered when checking for
     * duplicates. To forget and delete a recording, see
     * {@link #deleteRecording(Channel, Date, boolean, boolean)}.
     * 
     * @param program
     *            the program to be forgotten
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void forgetRecording(Program program) throws IOException;

    /**
     * Free a recorder that was previously locked with {@link #lockTuner(int)}.
     * 
     * @param recorderId
     *            the ID of the recorder to free
     * @return <code>true</code> if successful; <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public boolean freeTuner(int recorderId) throws IOException;

    /**
     * Ask the backend for a free recorder. The backend will attempt to find a
     * local recorder before remotes ones (see
     * {@link #getNextFreeRecorder(RecorderLocation)} for an alternative).
     * 
     * @return a free recorder or <code>null</code> if there are no free
     *         recorders
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public RecorderLocation getFreeRecorder() throws IOException;

    /**
     * Retrieve the number of recorders that are online, but not busy or
     * {@link #lockTuner(int) locked}.
     * 
     * @return the number of free recorders
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public int getFreeRecorderCount() throws IOException;

    /**
     * Retrieve a list of recorders that are online, but not busy or locked.
     * 
     * @return the list of free recorder IDs
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<Integer> getFreeRecorderList() throws IOException;

    /**
     * Starting with the given recorder, find the next free recorder in order.
     * This request will wrap from the end of the list back around to the
     * beginning.
     * 
     * @param from
     *            the backend will start looking at the next recorder in line
     *            after this one
     * @return the next available recorder or <code>null</code> if there are no
     *         free recorders
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public RecorderLocation getNextFreeRecorder(RecorderLocation from) throws IOException;

    /**
     * Retrieve a recorder's host and port information.
     * 
     * @param recorderId
     *            the ID of the recorder to lookup
     * @return the requested recorder information
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the given recorder ID is unknown
     * 
     * @since 63
     */
    public RecorderLocation getRecorderFromNum(int recorderId) throws IOException, CommandException;

    /**
     * Request the recorder that is actively recording the given program.
     * 
     * @param program
     *            the program for which a recorder is requested
     * @return the appropriate recorder or <code>null</code> if the program is
     *         not currently recording
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public RecorderLocation getRecorderNum(Program program) throws IOException;

    /**
     * Request that a slave backend go to sleep. This request will have no
     * affect on the master backend.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if an error occurs while try to put the slave backend to
     *             sleep
     * 
     * @since 63
     */
    public void goToSleep() throws IOException, CommandException;

    /**
     * Request that the given recorder be locked for exclusive use. When the
     * recorder is no longer needed, {@link #freeTuner(int)} should be called to
     * release it.
     * 
     * @param recorderId
     *            the ID of desired recorder, a value less than zero indicates
     *            no preference
     * @return device information for the recorder that was locked
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the tuner cannot be locked
     * 
     * @since 63
     */
    public RecorderDevice lockTuner(int recorderId) throws IOException, CommandException;

    /**
     * Request that the backend clear cached settings.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void messageClearSettingsCache() throws IOException;

    /**
     * Request that the backend reset the idle period used to determine when the
     * machine will automatically shutdown. After this message is received, the
     * clock will start over.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void messageResetIdleTime() throws IOException;

    /**
     * Change the verbose logging options on the backend.
     * 
     * @param options
     *            the options to set
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to set the verbose logging options
     * 
     * @since 63
     */
    public void messageSetVerbose(List<Verbose> options) throws IOException, CommandException;

    /**
     * Retrieve the bookmark set on a recording.
     * 
     * @param channel
     *            the channel on which the program was recorded
     * @param recStartTs
     *            the actual (not scheduled) start time of the recording
     * @return the bookmark location in number of frames or <code>0</code if
     *         there is no bookmark
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException;

    /**
     * Retrieve a URI to the given program.
     * 
     * @param checkSlaves
     *            if <code>true</code>, the backend will contact other backends
     *            to look for the given program; otherwise only the connected
     *            backend will be checked
     * @param program
     *            the program whose URI is requested
     * @return the URI of the given program or <code>null</code> if the
     *         program's file cannot be found
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public URI queryCheckFile(boolean checkSlaves, Program program) throws IOException;

    /**
     * Retrieve the list of commercial breaks found for the the recording at the
     * given channel and start time.
     * 
     * @param channel
     *            the channel on which the program aired
     * @param recStartTs
     *            the actual (not scheduled) start time of the program
     * @return the list of commercial breaks
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException;

    /**
     * Retrieve the list of cut list marks found for the the recording at the
     * given channel and start time.
     * 
     * @param channel
     *            the channel on which the program aired
     * @param recStartTs
     *            the actual (not scheduled) start time of the program
     * @return the list of cut marks
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException;

    /**
     * Determine whether or not a file exists and, if it does exists, its full
     * path.
     * 
     * @param filename
     *            the location of the file to check relative to a storage group
     * @param storageGroup
     *            the name of the storage group to check (starting with version
     *            65, this can be <code>null</code>, in which case the default
     *            group will be used)
     * @return if it exists, information about the file; <code>null</code>
     *         otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException;

    /**
     * Retrieve a 64-bit hash of the file represented by the given name and
     * storage group.
     * 
     * @param filename
     *            the location of the file to hash relative to a storage group
     * @param storageGroup
     *            the storage group containing the file to has (optional, this
     *            will default to the default storage group)
     * @return the hash value or <code>null</code> if the hash could not be
     *         computed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to determine the hash value
     * 
     * @since 63 <!-- @deprecated 69 - see
     *        {@link #queryFileHash(URI, String, String)} -->
     */
    public String queryFileHash(URI filename, String storageGroup) throws IOException,
                                                                  CommandException;

    /**
     * Retrieve a 64-bit hash of the file represented by the given name and
     * storage group.
     * 
     * @param filename
     *            the location of the file to hash relative to a storage group
     * @param storageGroup
     *            the storage group containing the file to hash (optional, this
     *            will default to the default storage group)
     * @param host
     *            the host containing the file (optional, this will default to
     *            the connected backend)
     * @return the hash value or <code>null</code> if the hash could not be
     *         computed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to determine the hash value
     * 
     * @since 69
     */
    public String queryFileHash(URI filename, String storageGroup, String host) throws IOException,
                                                                               CommandException;

    /**
     * Determine how much space is available on all drives connected to this
     * backend.
     * 
     * @return a list of drives with detailed space information
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<DriveInfo> queryFreeSpace() throws IOException;

    /**
     * Determine overall space used and free in the entire MythTV environment
     * (across all backends).
     * 
     * @return drive data representing a summary across the environment, only
     *         the space information will be valid
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public DriveInfo queryFreeSpaceSummary() throws IOException;

    /**
     * Request that the backend create a pix map for the given program.
     * 
     * @param id
     *            a unique identifier
     * @param program
     *            the program for which the pix map is to be generated
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend encounters an error
     * 
     * @since 63
     */
    public void queryGenPixMap2(String id, Program program) throws IOException, CommandException;

    /**
     * Retrieve a list of all scheduled recordings that are coming up soon.
     * 
     * @return the list of pending recordings
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public UpcomingRecordings queryGetAllPending() throws IOException;

    /**
     * Retrieve a list of all scheduled recordings.
     * 
     * @return the list of scheduled recordings
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<Program> queryGetAllScheduled() throws IOException;

    /**
     * Get a list of programs that conflict with the given program.
     * 
     * @param program
     *            the program to check for conflicts
     * @return a list of conflicting programs
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<Program> queryGetConflicting(Program program) throws IOException;

    /**
     * Retrieve a list of all recordings set to auto-expire soon.
     * 
     * @return the list of expiring recordings
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<Program> queryGetExpiring() throws IOException;

    /**
     * Retrieve the end date of current EPG data. This is typically about two
     * weeks from the last update. This should not be confused with any
     * expiration date associated with guide subscriptions.
     * 
     * @return latest date for which guide data is present
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public Date queryGuideDataThrough() throws IOException;

    /**
     * Retrieve the host name of the connected backend.
     * 
     * @return backend's hostname
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public String queryHostname() throws IOException;

    /**
     * Determine whether or not the given hostname has an active backend
     * instance.
     * 
     * @param hostname
     *            the hostname to check
     * @return <code>true</code> if an active backend is found at the given
     *         host; <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public boolean queryIsActiveBackend(String hostname) throws IOException;

    /**
     * Retrieve a list of hostnames of currently active backends.
     * 
     * @return the list of active backends
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 72
     */
    public List<String> queryActiveBackends() throws IOException;

    /**
     * Retrieve a count of the recordings in progress.
     * 
     * @return stats on recordings in progress
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public RecordingsInProgress queryIsRecording() throws IOException;

    /**
     * Retrieve the load factor of the backend machine.
     * 
     * @return data representing the load factor over time of the backend
     *         machine
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public Load queryLoad() throws IOException;

    /**
     * Retrieve memory statistics of the backend machine.
     * 
     * @return data representing the current memory usage of the backend machine
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public MemStats queryMemStats() throws IOException;

    /**
     * Retrieve the pix map for a program if it was last modified after the
     * given timestamp and it is under the given max size.
     * 
     * @param timestamp
     *            the timestamp to check against for modification (
     *            <code>null</code> ignores the timestamp)
     * @param maxFileSize
     *            the maximum size to accept
     * @param program
     *            the program to check
     * @return the requested pix map
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if there is an error in retrieving the information or no
     *             match to the given criteria
     */
    public PixMap queryPixMapGetIfModified(Date timestamp, int maxFileSize, Program program) throws IOException,
                                                                                            CommandException;

    /**
     * Retrieve the last modified date of the current pix map for the given
     * program.
     * 
     * @param program
     *            the program to check
     * @return the last modified date or <code>null</code> if the date could not
     *         be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public Date queryPixMapLastModified(Program program) throws IOException;

    /**
     * Get a sub-protocol object that provides an API to interrogate a specific
     * recorder. This API focuses on the recorder itself (such as brightness and
     * color options) as well as some recording information.
     * 
     * @see #queryRemoteEncoder(int)
     * 
     * @param recorderId
     *            the ID of the recorder to interrogate
     * @return an object that provides capabilities to interrogate the specified
     *         recorder
     */
    public QueryRecorder queryRecorder(int recorderId);

    /**
     * Retrieve the program data associated with the given recording by its base
     * filename.
     * 
     * @see #queryRecordingTimeslot(Channel, Date)
     * 
     * @param basename
     *            the base filename of the program to lookup
     * @return the relevant program data
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend cannot determine recording information
     * 
     * @since 63
     */
    public Program queryRecordingBasename(String basename) throws IOException, CommandException;

    /**
     * Retrieve the program data associated with the given channel and start
     * time.
     * 
     * @see #queryRecordingBasename(String)
     * 
     * @param channel
     *            the channel of the program to lookup
     * @param recStartTs
     *            the actual (not scheduled) start time of the program to lookup
     * @return the relevant program data
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend cannot determine recording information
     * 
     * @since 63
     */
    public Program queryRecordingTimeslot(Channel channel, Date recStartTs) throws IOException,
                                                                           CommandException;

    /**
     * Retrieve a list of recordings matching the given category.
     * 
     * @param recCategory
     *            the category of recordings to get
     * @return a list of programs representing recordings of the given category
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public List<Program> queryRecordings(RecordingCategory recCategory) throws IOException;

    /**
     * Get a sub-protocol object that provides an API to interrogate a specific
     * recorder. This API is focused on recordings and the state of the
     * recorder.
     * 
     * @see #queryRecorder(int)
     * 
     * @param recorderId
     *            the ID of the recorder to interrogate
     * @return an object that provides capabilities to interrogate the specified
     *         recorder
     */
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId);

    /**
     * Retrieve the current value of a setting.
     * 
     * @param host
     *            the host to which the setting pertains
     * @param name
     *            the name of the setting
     * @return the value of the setting or <code>null</code> if there is no
     *         value
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public String querySetting(String host, String name) throws IOException;

    /**
     * Retrieve file information (i.e. last modified date, file size) for a file
     * within a storage group.
     * 
     * @param host
     *            the host machine containing the file
     * @param storageGroup
     *            the storage group containing the file
     * @param path
     *            the full path to the file
     * @return the file information or <code>null</code> if the file cannot be
     *         found (which may be temporary, such as when a slave backend is
     *         unavailable)
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the host is a slave backend and is not reachable
     * 
     * @since 63
     */
    public FileInfo querySgFileQuery(String host, String storageGroup, String path) throws IOException,
                                                                                   CommandException;

    /**
     * Retrieve a complete directory listing within a storage group.
     * 
     * @param host
     *            the host machine containing the directory
     * @param storageGroup
     *            the storage group containing the directory
     * @param path
     *            the full path to the directory
     * @return a list of directories and files contained within the given path
     *         or <code>null</code> if the directory cannot be found (which may
     *         be temporary, such as when a slave backend is unavailable)
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the host is a slave backend and is not reachable
     * 
     * @since 63
     */
    public List<FileEntry> querySgGetFileList(String host, String storageGroup, String path) throws IOException,
                                                                                            CommandException;

    /**
     * Retrieve time information from the backend, including current date, time,
     * and time zone.
     * 
     * @return the time data
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public TimeInfo queryTimeZone() throws IOException;

    /**
     * Determine the up time, in seconds, of the backend machine.
     * 
     * @return the number of seconds the backend has been up or <code>-1</code>
     *         if the up time could not be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public long queryUptime() throws IOException;

    /**
     * Refresh the backend configuration.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void refreshBackend() throws IOException;

    /**
     * Request the scheduler to run.
     * 
     * @param recorderId
     *            the ID of the recorder for which the scheduler should run or
     *            <code>-1</code> to request a full run
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void rescheduleRecordings(int recorderId) throws IOException;

    /**
     * Request a scan of videos. Listen for a backend event to provide notice
     * when the scan completes.
     * 
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 64
     */
    public void scanVideos() throws IOException;

    /**
     * Set the bookmark for the program matching the given channel and time.
     * 
     * @param channel
     *            the channel on which the program was recorded
     * @param recStartTs
     *            the actual (not scheduled) start time of the recording
     * @param location
     *            the location in number of frames to set the bookmark
     * @return <code>true</code> if the bookmark was set successfully;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to set the bookmark
     * 
     * @since 63
     */
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException,
                                                                               CommandException;

    /**
     * Replace a channel with new information.
     * 
     * @param oldChannel
     *            the channel being replaced/modified
     * @param newChannel
     *            the new channel information
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to set the channel information
     * 
     * @since 63
     */
    public void setChannelInfo(Channel oldChannel, Channel newChannel) throws IOException,
                                                                      CommandException;

    /**
     * Request that the given recorder put the next LiveTV recording in the
     * directory specified by the given path.
     * 
     * @param recorderId
     *            the ID of the recorder to change
     * @param path
     *            the path of the directory to set
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the backend is unable to set the directory
     * 
     * @since 63
     */
    public void setNextLiveTvDir(int recorderId, String path) throws IOException, CommandException;

    /**
     * Update the value of a setting.
     * 
     * @param host
     *            the host to which the setting pertains
     * @param name
     *            the name of the setting
     * @param value
     *            the value to set
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void setSetting(String host, String name, String value) throws IOException;

    /**
     * Request a slave backend to shut down with the given command. This request
     * will have no affect on the master backend.
     * 
     * @param command
     *            the shutdown command
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public void shutdownNow(String command) throws IOException;

    /**
     * Request that the given program stop recording.
     * 
     * @param program
     *            the program to stop recording
     * @return 0 if the recording is on a slave backend, -1 if an error occurs,
     *         or the recorder ID that was recording the program
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public int stopRecording(Program program) throws IOException;

    /**
     * Request that the a program marked for future deletion be unmarked.
     * 
     * @param program
     *            the program to undelete
     * @return <code>true</code> if the request was successful;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * 
     * @since 63
     */
    public boolean undeleteRecording(Program program) throws IOException;

    /**
     * Retrieve a list of constants of the given type that are valid for this
     * protocol.<br>
     * <br>
     * This is useful to know before trying a command that a certain option is
     * valid. For example, to use {@link #queryRecordings(RecordingCategory)}
     * without the risk of sending an invalid category, first make sure it is in
     * the list returned from
     * <code>getAvailableTypes(RecordingCategory.class)</code>
     * 
     * @param <E>
     *            the generic enum type
     * @param type
     *            the enum class
     * @return a list of constants that are valid for this protocol
     */
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type);

    /**
     * Add a listener to receive unsolicited backend event messages.
     * 
     * @param l
     *            the listener to add
     */
    public void addBackendEventListener(BackendEventListener l);

    /**
     * Remove the given listener from receiving backend event messages.
     * 
     * @param l
     *            the listener to remove
     */
    public void removeBackendEventListener(BackendEventListener l);

    /**
     * Copy all backend event listeners from this protocol to the given
     * protocol.
     * 
     * @param protocol
     *            the destination protocol
     */
    public void copyBackendEventListeners(Protocol protocol);
}
